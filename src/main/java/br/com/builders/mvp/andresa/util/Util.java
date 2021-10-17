package br.com.builders.mvp.andresa.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Util {
	
	public static DateFormat dataFormato = new SimpleDateFormat("dd-MM-yyyy");
	
	/**
	 * Esse método compara duas datas e verifica se o dia e mês são iguais. Caso positivo retorna true, caso negativo, retorna false
	 * @author andresa
	 * @param data1
	 * @param data2
	 * @return se o mes e dia sao iguais
	 */
	public static boolean comparaDiaEMes(Date data1, Date data2) {
		String diaMes1 = getDia(data1)+getMes(data1);
		String diaMes2 = getDia(data2)+getMes(data2);
		return diaMes1.equals(diaMes2);
	}
	
	/**
	 * @author andresa
	 * @param data
	 * @return idade
	 */
	public static int calculoIdade(Date data) {
		int dia = Integer.parseInt(getDia(data));
		int mes = Integer.parseInt(getMes(data));
		int ano = Integer.parseInt(getAno(data));
	    return (int) ChronoUnit.YEARS.between(LocalDate.of(ano, mes, dia), LocalDate.now());
	}
	
	/**
	 * Retorna o dia da data informada
	 * @author andresa
	 * @param data
	 * @return
	 */
	public static String getDia(Date data) {
		String dia = "";
		if(data == null) {
			return "";
		}
		String dataString = dataFormato.format(data);
		if(dataString.length() == 10) {
			dia = dataString.substring(0,2);
		}
		return dia;
	}
	
	/**
	 * Retorna o mes da data informada
	 * @author andresa
	 * @param data
	 * @return
	 */
	public static String getMes(Date data) {
		String mes = "";
		if(data == null) {
			return "";
		}
		String dataString = dataFormato.format(data);
		if(dataString.length() == 10) {
			 mes = dataString.substring(3,5);
		}
		
		return mes;
	}
	
	/**
	 * Retorna o ano da data informada
	 * @author andresa
	 * @param data
	 * @return
	 */
	public static String getAno(Date data) {
		String ano = "";
		if(data == null) {
			return "";
		}
		String dataString = dataFormato.format(data);
		if(dataString.length() == 10) {
			 ano = dataString.substring(6,10);
		}
		return ano;
	}
}

