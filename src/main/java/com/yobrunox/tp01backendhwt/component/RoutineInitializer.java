package com.yobrunox.tp01backendhwt.component;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class RoutineInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final ResourceLoader resourceLoader;

    @Override
    public void run(String... args) throws Exception {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM routine", Integer.class);

        if (count != null && count == 0) {
            Resource resource = resourceLoader.getResource("classpath:static/data-routine.sql");

            if (resource.exists()) {
                String sqlScript = FileCopyUtils.copyToString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
                jdbcTemplate.execute(sqlScript);
                System.out.println(">>> Script en static/data.sql ejecutado exitosamente.");
            } else {
                System.err.println(">>> Error: No se encontró el archivo en classpath:static/data.sql");
            }
        } else {
            System.out.println(">>> La tabla ya contiene datos. Omitiendo ejecución.");
        }
    }
}