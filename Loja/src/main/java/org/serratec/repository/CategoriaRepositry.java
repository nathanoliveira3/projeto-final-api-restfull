package org.serratec.repository;

import org.serratec.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositry extends JpaRepository<Categoria, Long> {

}
