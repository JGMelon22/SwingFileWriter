import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.BorderLayout;
import java.awt.Cursor;

import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainForm {

	private JFrame frmFileWriter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmFileWriter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {

		try {
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}

		initialize();
		frmFileWriter.setResizable(false);
		frmFileWriter.setLocationRelativeTo(null);

		Image img = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/logo.png"));
		frmFileWriter.setIconImage(img);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmFileWriter = new JFrame();
		frmFileWriter.setTitle("File Writer");
		frmFileWriter.setBounds(100, 100, 450, 300);
		frmFileWriter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLayeredPane layeredPane = new JLayeredPane();
		frmFileWriter.getContentPane().add(layeredPane, BorderLayout.CENTER);

		JButton btnWrite = new JButton("Write!");

		btnWrite.setBounds(313, 231, 117, 25);
		btnWrite.setCursor(new Cursor(Cursor.HAND_CURSOR));
		layeredPane.add(btnWrite);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 418, 142);
		layeredPane.add(scrollPane);

		JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		btnWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Thread t1 = new Thread(() -> {

					// Conteúdo texto
					String texto = textPane.getText();

					String caminhoAtual = Paths.get("").toAbsolutePath().toString();
					Path path = Paths.get(caminhoAtual + "/Arquivo.txt");

					if (texto.isEmpty() || texto == "") {
						JOptionPane.showMessageDialog(null, "Texto vazio!", "Error", JOptionPane.ERROR_MESSAGE);
					}

					else {

						try {
							Files.write(path, texto.getBytes());
						} catch (IOException e) {
							JOptionPane.showMessageDialog(null, textPane, e.getMessage(), JOptionPane.ERROR_MESSAGE);
						}

						JOptionPane.showMessageDialog(null, "Arquivo Escrito!", "Mensagem", JOptionPane.PLAIN_MESSAGE);
					}
				});
				t1.start();

			}
		});

	}
}
