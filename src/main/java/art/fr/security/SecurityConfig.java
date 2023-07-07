package art.fr.security;


import art.fr.entities.Customer;
import art.fr.repositosy.CustomerRepository;
import art.fr.service.CustomerService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final RsaKeysConfig rsaKeysConfig;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;

    public SecurityConfig(RsaKeysConfig rsaKeysConfig, PasswordEncoder passwordEncoder, CustomerRepository customerRepository) {
        this.rsaKeysConfig = rsaKeysConfig;
        this.passwordEncoder = passwordEncoder;
        this.customerRepository = customerRepository;
    }


    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService){
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return  email->{
            Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if(customer.isEmpty()){
                throw new UsernameNotFoundException("No user found");
            }
            customer.get().getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
            return new User(customer.get().getEmail(), customer.get().getPassword(), authorities);
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeRequests(auth->auth.antMatchers(HttpMethod.OPTIONS).permitAll())
                .authorizeRequests(auth->auth.antMatchers("/api/artere-cs/products","/api/artere-cs/product/picture/**", "/api/artere-cs/customer","/api/artere-cs/customer/login", "/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**").permitAll())
                .authorizeRequests(auth->auth.anyRequest().authenticated())
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey
                .Builder(rsaKeysConfig.publicKey())
                .privateKey(rsaKeysConfig.privateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeysConfig.publicKey()).build();
    }

}
