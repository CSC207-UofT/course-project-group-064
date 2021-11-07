import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BoardGui extends JFrame implements MouseMotionListener, MouseListener {
    JLayeredPane pane;
    JPanel board;
    JLabel piece;
    int xAdjustment;
    int yAdjustment;

    public BoardGui(){
        //Add the pane for the board and mouse listeners for user interaction
        pane = new JLayeredPane();
        getContentPane().add(pane);
        pane.addMouseListener(this);
        pane.addMouseMotionListener(this);

        //Add the chess board as a panel to the pane
        board = new JPanel();
        pane.add(board, JLayeredPane.DEFAULT_LAYER);

        //Create the chess board grid
        //Set the size of the board on the screen, hopefully resizable in the future
        board.setLayout( new GridLayout(8, 8) );
        Dimension boardDimensions = new Dimension(800, 800);
        pane.setPreferredSize(boardDimensions);
        board.setPreferredSize( boardDimensions );
        board.setBounds(0, 0, boardDimensions.width, boardDimensions.height);
        //Use a loop to add square panels to the board
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            board.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.black : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.black);
        }

        for (int i = 0; i < 16; i++) {
            JLabel piece = new JLabel(new ImageIcon(new ImageIcon("src/chess.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            JPanel panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }

        for (int i = 48; i < 64; i++) {
            JLabel piece = new JLabel(new ImageIcon(new ImageIcon("src/chess.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            JPanel panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }

    }

    public void mousePressed(MouseEvent e){
        piece = null;
        Component c =  board.findComponentAt(e.getX(), e.getY());

        if (c instanceof JPanel)
            return;

        Point parentLocation = c.getParent().getLocation();
        xAdjustment = parentLocation.x - e.getX();
        yAdjustment = parentLocation.y - e.getY();
        piece = (JLabel)c;
        piece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
        piece.setSize(piece.getWidth(), piece.getHeight());
        pane.add(piece, JLayeredPane.DRAG_LAYER);
    }


    public void mouseDragged(MouseEvent me) {
        if (piece == null) return;
        piece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    public void mouseReleased(MouseEvent e) {
        if(piece == null) return;

        piece.setVisible(false);
        Component c =  board.findComponentAt(e.getX(), e.getY());

        if (c instanceof JLabel){
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( piece );
        }
        else {
            Container parent = (Container)c;
            parent.add( piece );
        }

        piece.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new BoardGui();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        frame.setVisible(true);
    }
}

