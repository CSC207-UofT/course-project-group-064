import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
public class GameGui extends JFrame implements MouseMotionListener, MouseListener {
    JLayeredPane pane;
    JPanel board;
    JLabel piece;
    int xAdjustment;
    int yAdjustment;
    String pieceOrigin = "null";
    String pieceDestination = "null";
    boolean moveMade = false;


    public GameGui(Game game){
        //Add the pane for the board and mouse listeners for user interaction
        Game currentGame = game;
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
            square.setName(String.valueOf(i));
            board.add(square);

            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.GRAY);
        }

    }

    public static void clearGui(GameGui gui, String pieceDestination){
        for (int j = 0; j < 64; j++) {
            JPanel panel = (JPanel)gui.board.getComponent(j);
            panel.removeAll();
            if (pieceDestination != "null") {
                gui.board.getComponent(Integer.parseInt(pieceDestination)).setVisible(false);
                gui.board.getComponent(Integer.parseInt(pieceDestination)).setVisible(true);
            }
        }
    }

    public static void updateGui(Game game, GameGui gui) {
        Map currentBoard = game.board.getPiecePositions();
        for (int i = 0; i < 64; i++) {
            if (currentBoard.containsKey(i)) {
                boolean color = game.board.getPiecePositions().get(i).getColor();
                char colorString = 'B';
                if (color){
                    colorString = 'W';
                }
                String srcString = "src/chessPieces/" + currentBoard.get(i).toString().substring(0, 2) + colorString + ".png";
                JLabel piece = new JLabel(new ImageIcon(new ImageIcon(srcString).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                JPanel panel = (JPanel)gui.board.getComponent(i);
                panel.add(piece);
            }
        }
    }

    public void mousePressed(MouseEvent e){
        piece = null;
        Component c =  board.findComponentAt(e.getX(), e.getY());
        pieceOrigin = c.getParent().getName();

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
            pieceDestination = c.getParent().getName();
            Container parent = c.getParent();
            parent.remove(0);
            parent.add( piece );
            moveMade = true;
        }
        else {
            pieceDestination = c.getName();
            Container parent = (Container)c;
            parent.add( piece );
            moveMade = true;
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
        Game game = new Game("Standard");

        GameGui frame = new GameGui(game);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        clearGui(frame, frame.pieceDestination);
        updateGui(game, frame);
        frame.setVisible(true);

        game.standardDisplay();

        String moveString = frame.pieceOrigin + "," + frame.pieceDestination;
        while(moveString != "end") {
            frame.moveMade = false;
            while (!frame.moveMade) {
                moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            }
            moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            String [] orDest = moveString.split(",");
            int origin = Integer.parseInt(orDest[0]);
            int destination = Integer.parseInt(orDest[1]);
            if (game.board.checkMoveLegal(origin, destination)) {
                int moveResult = game.board.makePlayerMove(origin, destination);
                if (moveResult == 2){
                    JOptionPane.showMessageDialog(frame,
                            "Check!",
                            "Game End",
                            JOptionPane.PLAIN_MESSAGE);
                    game.endGame(true);
                }
                else if (moveResult == 3){
                    JOptionPane.showMessageDialog(frame,
                            "Stalemate!",
                            "Game End",
                            JOptionPane.PLAIN_MESSAGE);
                    game.endGame(false);
                }
            }
            game.standardDisplay();
            clearGui(frame, frame.pieceDestination);
            updateGui(game, frame);
            frame.setVisible(true);
        }
    }
}
