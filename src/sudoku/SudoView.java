package sudoku;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

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
		
		byte[] puzzle = Sudoku.LoadSudokuFromFile(path);
		
		try {
			BacktrackingTechnique.solve(puzzle);
		} catch (Exception e) { }
		
		long start = 0;
		long stop = 0;
		
		if (puzzle == null) {
			System.err.println("Could not load puzzle.");
			System.exit(-2);
			return;
		}
		
		
		byte[][] puzzles = new byte[n][];
		for (int i = 0; i < n; ++i) {
			puzzles[i] = Arrays.copyOf(puzzle, puzzle.length);
		}
		
		byte[] solution = null;
		
		start = System.nanoTime();
		for (int i = 0; i < n; ++i)
			solution = Main.solve(puzzles[i]);
		stop = System.nanoTime();
		
		if (solution != null) {
			System.out.println(Sudoku.printSudoku(solution));
		} else {
			System.err.println("No solution found. Sorry, eh!");
		}
		
		System.out.println("Temps moyen : " + ((double) (stop - start)) / 1000000 / n + "ms");
		this.btnRun.setEnabled(true);
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	void close() {
		this.dispose();
	}

}
