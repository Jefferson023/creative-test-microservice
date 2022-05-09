package com.test.creative.repository;

import com.test.creative.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    @Query("{'nome' : {$regex: /.*?0.*/,$options:'i'}, 'email' : {$regex: /.*?1.*/,$options:'i'}, 'endereco' : {$in: [/.*?2.*/i, null] }, 'endereco' : {$in: [/.*?3.*/i, null] } }")
    Page<Usuario> findAllByFiltroPaginado(String nome, String email, String endereco, String telefone, Pageable pageable);
}
