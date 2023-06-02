package Modelo;

import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Hashtable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graficador {
	
	public static JFreeChart graficarSimple (Hashtable<String, Integer> dataSet, String titulo, String nombreX, String nombreY) 
	{
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		Enumeration<String> keys = dataSet.keys();
		while(keys.hasMoreElements()) 
		{
			String key = keys.nextElement();
			data.setValue(dataSet.get(key), "Valor", key);
		}
		
		
		JFreeChart grafico = ChartFactory.createBarChart3D(titulo, nombreX, nombreY, data);
		
		return grafico;
	}

}
