package com.car.insurance.api.security.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {

	@NotBlank(message =  "Nome não deve ser vazio")
	private String name;
	
	@NotBlank(message =  "Email não deve ser vazio")
	@Email(message = "Email deve ser um email válido")
	private String email;
	
	@NotBlank(message =  "Senha não deve ser vazio")
	@JsonProperty("senha")
	private String password;
	
	@NotBlank(message =  "Confirmação de Senha não deve ser vazia")
	@JsonProperty("confirmacaoSenha")
	private String passwordConfirmation;
	
	@CPF(message = "O CPF informado deve ser um CPF válido")
	private String cpf;
	
	@Past(message = "Data de nascimento deve ser anterior à data atual")
	@NotNull(message = "Data de nascimento deve estar preenchida.")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@JsonProperty("dataNascimento")
	private LocalDate birthDate;
	
	private String scope;
}
