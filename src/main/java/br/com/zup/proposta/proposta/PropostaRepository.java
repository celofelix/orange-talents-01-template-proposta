package br.com.zup.proposta.proposta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {

	Optional<Proposta> findByDocumento(String documento);

	@Query("SELECT p FROM Proposta p LEFT JOIN p.cartao c WHERE p.status = 'ELEGIVEL' and c is null")
	List<Proposta> findPropostasElegiveisSemCartaoOrderByIdAsc();

	List<Proposta> findByStatus(StatusProposta elegivel);

}
