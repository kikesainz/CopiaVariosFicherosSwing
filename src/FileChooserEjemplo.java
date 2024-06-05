import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileChooserEjemplo {
	static File archivo;
	static File directorio;
	static File[] arraydeArchivos;

	public static void main(String[] args) {

		JFrame ventana = new JFrame("Ejemplo FileChooser");

		JPanel panelBotoneraYStatus = new JPanel();
		panelBotoneraYStatus.setLayout(new FlowLayout());

		JButton botonAbrir = new JButton("Abrir Fichero");

		JButton botonAbrirDirectorio = new JButton("Abrir Directorio");
		JLabel statusFichero = new JLabel("No se ha seleccionado ningún fichero");
		JButton botonCopiar = new JButton("Copiar Fichero");
		botonCopiar.setEnabled(false);

		panelBotoneraYStatus.add(botonAbrir);
		panelBotoneraYStatus.add(statusFichero);
		panelBotoneraYStatus.add(botonAbrirDirectorio);
		panelBotoneraYStatus.add(botonCopiar);

		ventana.add(panelBotoneraYStatus, BorderLayout.NORTH);

//		Configurar el área de log
		JTextArea areaLog = new JTextArea();
		areaLog.setEditable(false);
		JScrollPane scroll = new JScrollPane(areaLog);
		ventana.add(scroll, BorderLayout.CENTER);

		botonAbrir.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setMultiSelectionEnabled(true);
			int resultadoEleccion = fileChooser.showOpenDialog(null);

			if (resultadoEleccion == JFileChooser.APPROVE_OPTION) {
//				archivo = fileChooser.getSelectedFile();
				 arraydeArchivos = fileChooser.getSelectedFiles();
				for (File f: arraydeArchivos) {
					statusFichero.setText("Se ha seleccionado fichero ");
					areaLog.append("Archivo Seleccionado: " + f.getName() + "\n");
				
				}

				validaSeleccion(botonCopiar);
			}

		});

		botonAbrirDirectorio.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int resultadoEleccion = fileChooser.showOpenDialog(null);

			if (resultadoEleccion == JFileChooser.APPROVE_OPTION) {
				directorio = fileChooser.getSelectedFile();
				System.out.println(directorio.getAbsolutePath());
				validaSeleccion(botonCopiar);
				areaLog.append("Directorio Seleccionado: " + directorio.getName() + "\n");

			}
		});

		botonCopiar.addActionListener(e -> {
			try {
				
				for (File f : arraydeArchivos) {
					File dirDestino = new File(directorio, f.getName());

					System.out.println(dirDestino.toString());
					Files.copy(f.toPath(), dirDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
					areaLog.append(
							"Fichero : " + f.getName() + " copiado con éxito a: " + directorio.getName() + "\n");
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		ventana.setSize(800, 500);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setVisible(true);

	}

	private static void validaSeleccion(JButton boton) {
		if (arraydeArchivos.length > 0 && directorio != null) {
			boton.setEnabled(true);
		}
	}

}
