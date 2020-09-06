package com.unesp.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);

		System.out.println("\nEntre com o seguinte comando no terminal para iniciar a API de recomendacao: \npython3 ../../../../python/app.py");
		System.out.println("\nOBS: Caso tenha problemas de dependência, execute os seguintes comandos no terminal:" +
				  		   "\nsudo apt install python3-pip" +
				           "\npip3 install pandas numpy pymongo flask" +
						   "\npython3 ../../../../python/app.py");
	}
}
