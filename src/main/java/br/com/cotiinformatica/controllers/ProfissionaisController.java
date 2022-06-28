package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.cotiinformatica.entities.Cliente;
import br.com.cotiinformatica.entities.Endereco;
import br.com.cotiinformatica.entities.Profissional;
import br.com.cotiinformatica.helpers.MD5Helper;
import br.com.cotiinformatica.repositories.IProfissionalRepository;
import br.com.cotiinformatica.requests.CriarContaPostRequest;
import br.com.cotiinformatica.requests.ProfissionalPostRequest;
import br.com.cotiinformatica.requests.ProfissionalPutRequest;
import br.com.cotiinformatica.responses.ProfissionalGetResponse;
import io.swagger.annotations.ApiOperation;

@Transactional
@Controller
public class ProfissionaisController 
{
	@Autowired
	private IProfissionalRepository profissionalRepository;
	
	@CrossOrigin
	@ApiOperation("Endpoint para consulta de profissionais.")
	@RequestMapping(value = "/api/profissionais", method = RequestMethod.GET)
	public ResponseEntity<List<ProfissionalGetResponse>> get()
	{	
		try
		{	
			List<ProfissionalGetResponse> lista = new ArrayList<ProfissionalGetResponse>();
			
			// consultando os profissionais cadastrados no banco de dados
			for(Profissional profissional : profissionalRepository.findAll())
			{	
				ProfissionalGetResponse response = new ProfissionalGetResponse();
				response.setIdProfissional(profissional.getIdProfissional());
				response.setNome(profissional.getNome());
				response.setTelefone(profissional.getTelefone());	
				lista.add(response);
			}

			// HTTP 200 (OK)
			return ResponseEntity.status(HttpStatus.OK).body(lista);
		}
		catch(Exception e)
		{
			// HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@CrossOrigin
	@ApiOperation("Endpoint para consulta de 1 profissional.")
	@RequestMapping(value = "/api/profissionais/{id}", method = RequestMethod.GET)
	public ResponseEntity<ProfissionalGetResponse> get(Integer id)
	{
		try
		{
			ProfissionalGetResponse resultado = new ProfissionalGetResponse();
			Optional<Profissional> repository = profissionalRepository.findById(id);
			if (repository != null)
			{
				Profissional profissional = repository.get();
				resultado.setIdProfissional(profissional.getIdProfissional());
				resultado.setNome(profissional.getNome());
				resultado.setTelefone(profissional.getTelefone());	
			}

			// HTTP 200 (OK)
			return ResponseEntity.status(HttpStatus.OK).body(resultado);
		}
		catch(Exception e)
		{
			// HTTP 500 (INTERNAL SERVER ERROR)
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@CrossOrigin
	@ApiOperation("Endpoint para cadastro de profissionais.")
	@RequestMapping(value = "/api/profissionais", method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody ProfissionalPostRequest request)
	{
		try
		{			
			// capturando e salvando os dados do profissional
			Profissional profissional = new Profissional();			
			profissional.setNome(request.getNome());
			profissional.setTelefone(request.getTelefone());
			profissionalRepository.save(profissional);
			
			// HTTP 201 (CREATED)
			return ResponseEntity.status(HttpStatus.CREATED).body("Parabéns! Seu profissional foi cadastrado com sucesso.");
		}
		catch(IllegalArgumentException e)
		{
			// HTTP 400 (CLIENT ERROR) -> BAD REQUEST
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch(Exception e)
		{			
			// HTTP 500 (SERVER ERROR) -> INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@CrossOrigin
	@ApiOperation("Endpoint para atualização de profissionais.")
	@RequestMapping(value = "/api/profissionais", method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody ProfissionalPutRequest request)
	{
		try
		{
			Optional<Profissional> oldRegistro = profissionalRepository.findById(request.getIdProfissional());
	        if (!oldRegistro.isPresent())
	        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");

			// capturando e salvando os dados
        	Profissional newRegistro = oldRegistro.get();
        	newRegistro.setNome(request.getNome());
        	newRegistro.setTelefone(request.getTelefone());
        	profissionalRepository.save(newRegistro);

        	// HTTP 200 (OK)
			return ResponseEntity.status(HttpStatus.OK).body("Parabéns! Seu profissional foi alterado com sucesso.");								
		}
		catch (IllegalArgumentException e)
		{
			// HTTP 400 (CLIENT ERROR) -> BAD REQUEST
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch(Exception e)
		{			
			// HTTP 500 (SERVER ERROR) -> INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@CrossOrigin
	@ApiOperation("Endpoint para cadastro de profissionais.")
	@RequestMapping(value = "/api/profissionais/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable(value = "id") Integer id)
	{
		try
		{
			Optional<Profissional> registro = profissionalRepository.findById(id);
			if (!registro.isPresent())
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registro não encontrado.");
			profissionalRepository.delete(registro.get());

			// HTTP 200 (OK)
			return ResponseEntity.status(HttpStatus.OK).body("Parabéns! Seu profissional foi excluído com sucesso.");	
		}
		catch (IllegalArgumentException e)
		{
			// HTTP 400 (CLIENT ERROR) -> BAD REQUEST
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
		catch (Exception e)
		{
			// HTTP 500 (SERVER ERROR) -> INTERNAL SERVER ERROR
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}