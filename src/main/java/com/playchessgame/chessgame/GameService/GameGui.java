package com.playchessgame.chessgame.GameService;

import com.playchessgame.chessgame.Entities.Game;
import com.playchessgame.chessgame.Entities.PlayerUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

@Service
public class GameGui extends JFrame implements MouseMotionListener, MouseListener {
    JLayeredPane pane;
    JPanel board;
    JLabel piece;
    int xAdjustment;
    int yAdjustment;

    private PlayerUser user1;
    private PlayerUser user2;

    public GameGui(PlayerUser user1, PlayerUser user2){
        this.user1 = user1;
        this.user2 = user2;
    }

    public GameGui(){
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
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.GRAY);
        }

        /*for (int i = 0; i < 16; i++) {
            JLabel piece = new JLabel(new ImageIcon(new ImageIcon("src/chess.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            JPanel panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }
        for (int i = 48; i < 64; i++) {
            JLabel piece = new JLabel(new ImageIcon(new ImageIcon("src/chess.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            JPanel panel = (JPanel)board.getComponent(i);
            panel.add(piece);
        }*/

        /*for(int i = 0; i < 64; i++) {
            Map currentBoard = game.board.getPiecePositions();
            if (currentBoard.containsKey(i)) {
                JLabel piece = new JLabel(new ImageIcon(new ImageIcon("src/chess.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                JPanel panel = (JPanel)board.getComponent(i);
                panel.add(piece);
            }*/

    }

    public static void updateGui(Game game, GameGui gui) {
        Map currentBoard = game.board.getPiecePositions();
        //gui.board = new JPanel();
        for (int i = 0; i < 64; i++) {
            if (currentBoard.containsKey(i)) {
                String[] pieceNameList = currentBoard.get(i).toString().split("\\.");
                String pieceName = pieceNameList[pieceNameList.length -1];
//                String srcString = "/Users/kaixinrongzi0218/IdeaProjects/springboot/ChessGame/src/main/java/chessPieces/" + currentBoard.get(i).toString().substring(0, 2) + ".png";
                String srcString = "src/main/java/chessPieces/" + pieceName.substring(0,2) + ".png";
                JLabel piece = new JLabel(new ImageIcon(new ImageIcon(srcString).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                JPanel panel = (JPanel)gui.board.getComponent(i);
                panel.add(piece);
            }
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

        //TODO: to determine if the move is legal

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
        Game game = new Game("Standard");

        GameGui frame = new GameGui();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        updateGui(game, frame);
        frame.setVisible(true);

        game.standardDisplay();
        String move = game.getMove();
        while (!move.equals("end")){
            frame.dispose();
            //game.updateDisplay(move);

            GameGui frame1 = new GameGui();
            frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
            frame1.pack();
            frame1.setResizable(false);
            //frame1.setLocationRelativeTo( null );
            updateGui(game, frame1);
            frame1.setVisible(true);

            move = game.getMove();
            frame1.dispose();
        }
    }

    @Transactional
    @RequestMapping("/play")
    public void run() {
        Game game = new Game("Standard");

        GameGui frame = new GameGui();
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo( null );
        updateGui(game, frame);
        frame.setVisible(true);

        game.standardDisplay();
        String move = game.getMove();
        while (!move.equals("end")){
            frame.dispose();
            //game.updateDisplay(move);

            GameGui frame1 = new GameGui();
            frame1.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
            frame1.pack();
            frame1.setResizable(false);
            //frame1.setLocationRelativeTo( null );
            updateGui(game, frame1);
            frame1.setVisible(true);

            move = game.getMove();
            frame1.dispose();
        }
    }

}
