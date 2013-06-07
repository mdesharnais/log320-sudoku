package sudoku;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class SudoView extends JFrame {

	private GroupLayout layout;

	private JButton btnRun;
	private JButton btnQuit;
	
	private int n = 1;

	public SudoView() {
		this.setTitle("Sudoku");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		btnRun = new JButton("Résoudre");
		btnQuit = new JButton("Quitter");

		layout = new GroupLayout(this.getContentPane());

		btnRun.setMinimumSize(new Dimension(150, 20));
		btnQuit.setMinimumSize(new Dimension(150, 20));

		btnRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				solve();
			}
		});

		btnQuit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});

		layout.setHorizontalGroup(layout.createParallelGroup().addComponent(btnRun).addComponent(btnQuit));
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(btnRun).addComponent(btnQuit));
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);

		this.getContentPane().setLayout(layout);
		this.getContentPane().add(btnRun, BorderLayout.EAST);
		this.getContentPane().add(btnQuit, BorderLayout.WEST);
		this.pack();
	}
	
	public void setN(int n) {
		this.n = n;
	}

	void solve() {
		this.btnRun.setEnabled(false);
		this.setCursor(new Cursor(Cursor.WAIT_CURSOR));
		int option;

		JFileChooser fileChooser = new JFileChooser();
		option = fileChooser.showOpenDialog(this);

		if (option != JFileChooser.APPROVE_OPTION)
			return;
			
		String path = fileChooser.getSelectedFile().getAbsolutePath();
		
		byte[][] puzzle = Sudoku.LoadSudokuFromFile(path);
		
		try {
			new BacktrackingTechnique(puzzle).solve();
		} catch (Exception e) { }
		
		long start = 0;
		long stop = 0;
		
		if (puzzle == null) {
			System.err.println("Could not load puzzle.");
			System.exit(-2);
			return;
		}
		
		
		byte[][] solution = null;
		start = System.nanoTime();
		
		for (int i = 0; i < n; ++i) {
			BacktrackingTechnique b = new BacktrackingTechnique(puzzle);
			
			try {
				b.solve();
			} catch (SolutionNotFoundException e) {
			} catch (SolutionFoundException e) {
				solution = b.getSolution();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}

		stop = System.nanoTime();
		
		if (solution != null) {
			System.out.println(Sudoku.printSudoku(solution));
			System.out.println("Résolu " + n + " fois");
		} else {
			System.err.println("No solution found. Sorry, eh!");
		}
		
		System.out.println("Temps moyen : " + (double) (stop - start) / 1000000 / n + "ms");
		this.btnRun.setEnabled(true);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	void close() {
		System.exit(0);
	}

}
