package com.tarjetaza.bootstrap;

import com.tarjetaza.domain.User;
import com.tarjetaza.domain.WebRequest;
import com.tarjetaza.repository.UserRepository;
import com.tarjetaza.repository.WebRequestRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
public class TarjetazaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final WebRequestRepository webRequestRepository;
    private final PasswordEncoder passwordEncoder;

    public TarjetazaBootstrap(UserRepository userRepository, WebRequestRepository webRequestRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.webRequestRepository = webRequestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        // chilt cynthia hilt

        User administrador = new User("administrador",
                passwordEncoder.encode("123456"));

        administrador.setFirstName("Luciano");
        administrador.setLastName("Giannelli");
        administrador.setEmail("lucho_aglp@hotmail.com");

        userRepository.save(administrador);

        WebRequest request = new WebRequest();

        request.setNombre("Luciano");
        request.setApellido("Giannelli");
        request.setCalle("27");
        request.setPuerta("453");
        //request.setPiso(piso);
        //request.setDpto(dpto);
        request.setCp("A1900XXX");
        request.setProvincia("03");
        request.setLocalidad("La Plata");
        request.setTelefono("2215560423");
        request.setCodigoDocumento("1");
        request.setCuitCuil("20254585018");
        request.setFecNac("19760627");
        request.setEstadoCivil("2");
        request.setSexo("M");
        request.setEmail("lucho_aglp@hotmail.com");

        webRequestRepository.save(request);
    }
}
