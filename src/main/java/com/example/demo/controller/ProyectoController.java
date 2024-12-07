package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import com.example.demo.model.Persona;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método GET para obtener una lista de 10 números aleatorios
    @GetMapping("/numero")
    public List<Integer> getNumeros() {
        Random random = new Random();
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            numeros.add(random.nextInt(100));
        }
        return numeros;
    }

    // Método POST para separar una lista de números en pares e impares
    @PostMapping("/pares")
    public Map<String, List<Integer>> separarParesImpares(@RequestBody List<Integer> numeros) {
        List<Integer> pares = new ArrayList<>();
        List<Integer> impares = new ArrayList<>();
        for (Integer numero : numeros) {
            if (numero % 2 == 0) {
                pares.add(numero);
            } else {
                impares.add(numero);
            }
        }
        Map<String, List<Integer>> resultado = new HashMap<>();
        resultado.put("pares", pares);
        resultado.put("impares", impares);
        return resultado;
    }

    // Método POST para validar si una cadena es una dirección de correo correcta
    @PostMapping("/validacorreo")
    public boolean validarCorreo(@RequestBody String correo) {
        return correo.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    // Método POST para crear un archivo a partir de una lista de personas
    @PostMapping("/crearArchivo")
    public String crearArchivo(@RequestBody List<Persona> personas) {
        StringBuilder contenido = new StringBuilder();
        for (Persona persona : personas) {
            contenido.append(persona.getNombre()).append("|")
                     .append(persona.getApellidoPaterno()).append("|")
                     .append(persona.getApellidoMaterno()).append("|")
                     .append(persona.getEdad()).append("\n");
        }
        try {
            Files.write(Paths.get("personas.txt"), contenido.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al crear el archivo";
        }
        return "Archivo creado exitosamente";
    }

    @PostMapping("/guardarUsuario")
    public Long guardarUsuario(@RequestBody Usuario usuario) {
        Usuario guardado = usuarioRepository.save(usuario);
        return guardado.getId();
    }
}
