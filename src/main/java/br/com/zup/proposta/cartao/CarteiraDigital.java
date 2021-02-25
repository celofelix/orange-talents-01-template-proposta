package br.com.zup.proposta.cartao;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;

import com.sun.istack.NotNull;

@Entity
public class CarteiraDigital {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String idCarteira;

	@Enumerated(EnumType.STRING)
	private CarteirasEnum carteira;

	@Email
	private String email;

	@NotNull
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn
	private Cartao cartao;
	
	public CarteiraDigital() {
		
	}

	public CarteiraDigital(String id, String carteira, String email, Cartao cartao) {
		this.idCarteira = id;
		this.carteira = CarteirasEnum.toStatus(carteira);
		this.email = email;
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}

	public String getIdCarteira() {
		return idCarteira;
	}

	public CarteirasEnum getCarteira() {
		return carteira;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "CarteiraDigital [id=" + id + ", idCarteira=" + idCarteira + ", carteira=" + carteira + ", email="
				+ email + ", cartao=" + cartao + "]";
	}

}
