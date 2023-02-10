package com.br.var.solutions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/pessoa")
public class PessoaController {
    @GetMapping
    public ResponseEntity<Object> get() {
        PessoaRequest pessoaRequest1 = new PessoaRequest();
        pessoaRequest1.setNome("Samuel");
        pessoaRequest1.setSobrenome("Souza");
        pessoaRequest1.setEndereco("Avenida Estrada Velha, n°458");
        pessoaRequest1.setIdade(34);
        return ResponseEntity.ok(pessoaRequest1);
    }
    @GetMapping("/resumo")
    //request body junto ao corpo do body postman       //request param se desejamos validaçoes dos nossos metodos
    public ResponseEntity<Object> getPessoa(@RequestBody PessoaRequest pessoinha, @RequestParam(value = "valida_mundial") Boolean DesejaValidarMundial) {
        InformacoesIMC imc = new InformacoesIMC();
        InformacoesIR impostoDeRenda = new InformacoesIR();
        int anoNascimento = 0;
        String validaMundial = null;
        String saldoEmDolar = null;

        if (!pessoinha.getNome().isEmpty()) {
            log.info("Iniciando o processo de reumo da pessoa", pessoinha);

            if (Objects.nonNull(pessoinha.getPeso()) && Objects.nonNull(pessoinha.getAltura())) {
                log.info("Iniciando o Calculo IMC", pessoinha);
                imc = calculaImc(pessoinha.getPeso(), pessoinha.getAltura());
            }

            if (Objects.nonNull(pessoinha.getIdade())) {
                log.info("Iniciando o Calculo do ano de nascimento");
                anoNascimento = calculaanoNascimento(pessoinha.getIdade());
            }

            if (Objects.nonNull(pessoinha.getSalario())) {
                log.info("Iniciando o Calculo Imposto de Renda");
                impostoDeRenda = calculaFaixaimpostoRenda(pessoinha.getSalario());
            }

            //Paramentro de confirmação ou alert seguindo o request param.
            if (Boolean.TRUE.equals(DesejaValidarMundial)) {
                if (Objects.nonNull(pessoinha.getTime())) {
                    log.info("Vaidando o time de Coração tem Mundial", pessoinha);
                    validaMundial = calculoMundial(pessoinha.getTime());
                }
            }

            if (Objects.nonNull(pessoinha.getSaldo())) {
                log.info("Convertendo real em dolar", pessoinha);
                saldoEmDolar = convertSaldoEmDolar(pessoinha.getSaldo());
            }

            log.info("Montando o Objeto de retorno para o FrontEnd");
            PessoaResponse resumo = montarRespostaFrontEnd(pessoinha, imc, anoNascimento, impostoDeRenda, validaMundial, saldoEmDolar);
            return ResponseEntity.ok(resumo);
        }
        return ResponseEntity.noContent().build();
    }
    private PessoaResponse montarRespostaFrontEnd(PessoaRequest pessoa, InformacoesIMC imc, int anoNascimento, InformacoesIR impostoDeRenda, String validaMundial, String saldoEmDolar) {
        PessoaResponse response = new PessoaResponse();
        response.setNome(pessoa.getNome());
        response.setSobrenome(pessoa.getSobrenome());
        response.setAnoNascimento(anoNascimento);
        response.setAltura(pessoa.getAltura());
        response.setPeso(pessoa.getPeso());
        response.setImc(imc.getImc());
        response.setClassificacaoIMC(imc.getClassificacao());
        response.setIdade(pessoa.getIdade());
        response.setEndereco(pessoa.getEndereco());
        response.setTime(pessoa.getTime());
        response.setValidaMundial(validaMundial);
        response.setSalario(pessoa.getSalario());
        response.setSaldoEmDolar(saldoEmDolar);
        response.setSaldo(pessoa.getSaldo());
        response.setDeducao_IR(impostoDeRenda.getBaseDeCalculo());
        response.setALiquota_IR(impostoDeRenda.getAliquota());
        return response;
    }
    private String convertSaldoEmDolar(double saldo) {

        return String.valueOf(saldo / 5.11);
    }
    private String calculoMundial(String time) {

        if (time.equalsIgnoreCase("São Paulo")) {
            return "Parabéns, o seu time possui 3 mundiais de clubes conforme a Fifa";
        } else if (time.equalsIgnoreCase("Corinthias")) {
            return "Corinthias Apenas só tem um mundial de clubes o restante é roubo";
        } else if (time.equalsIgnoreCase("Santos")) {
            return "Parabéns, o seu time possui 2 Mundiais de clube conforme a FIFA";
        } else {
            return "Poxa que pena, continue torcendo para o seu time ganhar um mundial";
        }
    }
    private int calculaanoNascimento(int idade) {
        LocalDate datalocal = LocalDate.now();
        int anoAtual = datalocal.getYear();
        return anoAtual - idade;
    }
    private InformacoesIMC calculaImc(double peso, double altura) {
        InformacoesIMC imcCalculado = new InformacoesIMC();
       double imc = peso / (altura * altura);
        if (imc < 18.5) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("abaixo do peso.");
            return imcCalculado;
        } else if (imc > 18.5 && imc <= 24.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("peso ideal.");
            return imcCalculado;
        } else if (imc > 24.9 && imc <= 29.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("excesso de peso");
            return imcCalculado;
        } else if (imc > 29.9 && imc <= 34.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("Obsidade classe I");
            return imcCalculado;
        } else if (imc > 34.9 && imc <= 39.9) {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("Obsidade classe II");
            return imcCalculado;
        } else {
            imcCalculado.setImc(String.valueOf(imc));
            imcCalculado.setClassificacao("Obsidade classe III");
            return imcCalculado;
        }
    }
    private InformacoesIR calculaFaixaimpostoRenda(double salario) {
        InformacoesIR informacoesIR = new InformacoesIR();
        if (salario < 1903.98) {
            informacoesIR.setBaseDeCalculo(Double.parseDouble(String.valueOf(informacoesIR.getISENTO())));
            informacoesIR.setAliquota("Isento");
            return informacoesIR;
        } else if (salario > 1903.98 && salario < 2826.65) {
            informacoesIR.baseDeCalculo = ((salario / 100) * informacoesIR.getLiquota75());
            informacoesIR.setBaseDeCalculo(Double.parseDouble(String.valueOf(informacoesIR.getBaseDeCalculo())));
            informacoesIR.setAliquota("7,5%");
            return informacoesIR;

        } else if (salario > 2826.66 && salario < 3751.05) {
            informacoesIR.baseDeCalculo = ((salario / 100) * informacoesIR.getLiquota15());
            informacoesIR.setBaseDeCalculo(Double.parseDouble(String.valueOf(informacoesIR.getBaseDeCalculo())));
            informacoesIR.setAliquota("15%");
            return informacoesIR;

        } else if (salario >= 3751.06 && salario < 4664.68) {
            informacoesIR.baseDeCalculo = ((salario / 100) * informacoesIR.getLiquota22());
            informacoesIR.setBaseDeCalculo(Double.parseDouble(String.valueOf(informacoesIR.getBaseDeCalculo())));
            informacoesIR.setAliquota("22,5%");
            return informacoesIR;
        } else {
            informacoesIR.baseDeCalculo = ((salario / 100) * informacoesIR.getLiquota27());
            informacoesIR.setBaseDeCalculo(Double.parseDouble(String.valueOf(informacoesIR.getBaseDeCalculo())));
            informacoesIR.setAliquota("27,5%");
            return informacoesIR;
        }
    }
}

