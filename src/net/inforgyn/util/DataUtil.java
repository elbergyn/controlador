package net.inforgyn.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public class DataUtil {

	public static final String dateFormat = "dd/MM/yyyy";

	public static final String dateTimeFormat = "dd/MM/yyyy HH:mm";
	public static final String dateTimeSecondFormat = "dd/MM/yyyy HH:mm:ss";
	private static DateFormat formatoPadrao;
	private static Locale locale = FacesContext.getCurrentInstance()
			.getViewRoot().getLocale();
	private static final SimpleDateFormat simpleDateFormat;
	public static final String time = "HH:mm";
	public static final String timeSecond = "HH:mm:ss";

	static {
		formatoPadrao = new SimpleDateFormat(dateFormat, locale);
		simpleDateFormat = new SimpleDateFormat("", new Locale("pt", "br"));
		simpleDateFormat.setLenient(false);
	}

	public static boolean isNull(Date data) {
		return data == null;
	}

	public static boolean isNotNull(Date data) {
		return data != null;
	}

	public String diaSemana(Calendar data) {
		return "";
	}

	public static Date adicionarMes(Date date, Integer mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, mes);
		return calendar.getTime();
	}
	
	public static Date subtrairMes(Date date, Integer mes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -mes);
		return calendar.getTime();
	}

	public static Date alterarDiaData(Calendar calendar, Integer dia) {
		calendar.set(Calendar.DAY_OF_MONTH, dia);
		return calendar.getTime();
	}

	public static String converterDataString(Date data, String pattern) {
		if (data == null) {
			return null;
		}
		simpleDateFormat.applyPattern(pattern);
		try {
			return simpleDateFormat.format(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Date converterStringData(String dataStr, String pattern) {
		if (dataStr == null || dataStr.length() == 0) {
			return null;
		}
		simpleDateFormat.applyPattern(pattern);
		try {
			return simpleDateFormat.parse(dataStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean dataAnterior(Date primeira, Date segunda) {
		if (dataIgual(primeira, segunda)) {
			return false;
		}
		return primeira.before(segunda);
	}

	public static boolean dataPosterior(Date primeira, Date segunda) {
		if (dataIgual(primeira, segunda)) {
			return false;
		}
		return primeira.after(segunda);
	}

	public static Date getData(String data) {
		Date dt = null;
		try {
			dt = (Date) formatoPadrao.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dt;
	}

	public static Date getDataAtual() {
		Date dt = new Date();
		dt.setHours(0);
		dt.setMinutes(0);
		dt.setSeconds(0);
		return dt;
	}

	public static String getDataAtualString() {
		simpleDateFormat.applyPattern(dateFormat);
		try {
			return simpleDateFormat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDataHoraAtualString() {
		simpleDateFormat.applyPattern(dateTimeFormat);
		try {
			return simpleDateFormat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDataAtualCompletaString() {
		simpleDateFormat.applyPattern(dateTimeSecondFormat);
		try {
			return simpleDateFormat.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getDataPadraoString(Date data) {
		return formatoPadrao.format(data);
	}

	public static SelectItem[] getMeses() {
		SelectItem[] meses = new SelectItem[12];
		meses[0] = new SelectItem(1, "Janeiro");
		meses[1] = new SelectItem(2, "Feveiro");
		meses[2] = new SelectItem(3, "Março");
		meses[3] = new SelectItem(4, "Abril");
		meses[4] = new SelectItem(5, "Maio");
		meses[5] = new SelectItem(6, "Junho");
		meses[6] = new SelectItem(7, "Julho");
		meses[7] = new SelectItem(8, "Agosto");
		meses[8] = new SelectItem(9, "Setembro");
		meses[9] = new SelectItem(10, "Outubro");
		meses[10] = new SelectItem(11, "Novembro");
		meses[11] = new SelectItem(12, "Dezembro");
		return meses;
	}

	public static Long getTimeMillis() {
		return System.currentTimeMillis();
	}

	public static Date subtrairDias(Date date, Integer dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -dias);
		return calendar.getTime();
	}

	public static Integer getAno() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}

	public static Integer getMes() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.MONTH);
	}

	public static Integer getDia() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static Integer getDiaDaSemana(Calendar calendar) {
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static Integer getDiaDaSemana(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static Date criarData(Integer dia, Integer mes, Integer ano) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(ano, mes, dia);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}

	public static Date adicionarHora(Date data) {
		Calendar date = Calendar.getInstance();
		date.setTime(data);

		return date.getTime();
	}

	public static Date adicionarUltimaHoraDia(Date data) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static boolean dataIgual(Date primeira, Date segunda) {
		if (getDataPadraoString(primeira).equals(getDataPadraoString(segunda))) {
			return true;
		}
		return false;
	}

	public static int getHora() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static Date adicionarDias(Date date, int dias) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		return calendar.getTime();
	}

	public static String getDataHoraString(Date data) {

		if (data == null) {
			return null;
		}
		simpleDateFormat.applyPattern(dateTimeFormat);
		try {
			return simpleDateFormat.format(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
