package org.serratec.dto.produto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import org.apache.tomcat.util.codec.binary.Base64;
import org.serratec.exceptions.CategoriaException;
import org.serratec.model.Categoria;
import org.serratec.model.Produto;
import org.serratec.repository.CategoriaRepository;

public class ProdutoCadastrarDTO {

	@NotEmpty
	private String codigo;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String descricao;
	private Double preco;
	private Integer quantidade;
	private String imagem;
	private String categoria;

	public Produto toProduto(CategoriaRepository categoriaRepository) throws CategoriaException {
		
		Produto produto = new Produto();
		
		produto.setCodigo(this.codigo);
		produto.setNome(this.nome);
		produto.setDescricao(this.descricao);
		produto.setPreco(this.preco);
		produto.setEstoque(this.quantidade);
		produto.setDataCadastro(LocalDate.now());
		produto.setImagem(this.imagem);
		
		Categoria categ = categoriaRepository.findByNome(categoria)
				.orElseThrow(() -> new CategoriaException("Categoria não cadastrada"));
				
		produto.setCategoria(categ);
		
		
		/*if (this.imagem != null) {
			byte[] img = Base64.decodeBase64(this.imagem);
			String nomeArquivo = "E:\\Documentos\\Spring\\RestfulAPI\\Imagens\\prod_" + produto.getCodigo() + ".jpg";
			
			try {
				OutputStream out = new FileOutputStream(new File(nomeArquivo));
				out.write(img);
				out.close();
				produto.setImagem(nomeArquivo);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		return produto;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public String getImagem() {
		return imagem;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
