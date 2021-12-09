package com.playchessgame.chessgame.GameService;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.*;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;

public class GameGui extends JFrame implements MouseMotionListener, MouseListener {
    //init general UI layout for the board, pieces, and backing pane
    JLayeredPane pane;
    JPanel board;
    JLabel piece;
    //x and y adjustment for the mouse listener 
    //(so player doesn't have to hit square exactly)
    int xAdjustment;
    int yAdjustment;
    //trackers for the piece origin and destination
    String pieceOrigin = "null";
    String pieceDestination = "null";
    //board rows and columns (8x8 in chess)
    final static int NUM_BOARD_ROWS = 8;
    final static int NUM_BOARD_COLUMNS = 8;
    //tracker for if the player has made a move
    boolean moveMade = false;
    private static final Caretaker caretaker = new Caretaker();
    private static final Originator originator = new Originator();
    
    /**
     * GameGui
     */
    public GameGui(Game game){
        //Add the pane for the board and mouse listeners for player interaction
        Game currentGame = game;
        pane = new JLayeredPane();
        getContentPane().add(pane);
        pane.addMouseListener(this);
        pane.addMouseMotionListener(this);

        //Add the chess board as a panel to the pane
        board = new JPanel();
        pane.add(board, JLayeredPane.DEFAULT_LAYER);

        //Create the chess board grid
        //Set the size of the board on the screen
        board.setLayout( new GridLayout(NUM_BOARD_ROWS, NUM_BOARD_COLUMNS) );
        //Board dimensions on the screen in pixels. 800x800 is chosen as it is 
        //boardly compatible with most displays
        Dimension boardDimensions = new Dimension(800, 800);
        pane.setPreferredSize(boardDimensions);
        board.setPreferredSize( boardDimensions );
        board.setBounds(0, 0, boardDimensions.width, boardDimensions.height);
        //Use a loop to add square panels to the board
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel( new BorderLayout() );
            square.setName(String.valueOf(i));
            board.add(square);
            //choose colors based on even or odd
            int row = (i / 8) % 2;
            if (row == 0)
                square.setBackground(i % 2 == 0 ? Color.gray : Color.white);
            else
                square.setBackground(i % 2 == 0 ? Color.white : Color.GRAY);
        }

    }
    
    /**
     * Removes all pieces from board
     */
    public static void clearGui(GameGui gui, String pieceDestination){
        for (int j = 0; j < 64; j++) {
            JPanel panel = (JPanel)gui.board.getComponent(j);
            panel.removeAll();
            if (!pieceDestination.equals("null")) {
                gui.board.getComponent(Integer.parseInt(pieceDestination)).setVisible(false);
                gui.board.getComponent(Integer.parseInt(pieceDestination)).setVisible(true);
            }
        }
    }
    
    /**
     * Updates the gui by adding pieces and piece images to the board
     * piece positions are determined by the hash map
     */
    public static void updateGui(Game game, GameGui gui) {
        Map currentBoard = game.board.getPiecePositions();
        for (int i = 0; i < 64; i++) {
            if (currentBoard.containsKey(i)) {
                boolean color = game.board.getPiecePositions().get(i).getColor();
                char colorString = 'B';
                if (color){
                    colorString = 'W';
                }

                String[] pieceNameList = currentBoard.get(i).toString().split("\\.");
                String pieceName = pieceNameList[pieceNameList.length-1].substring(0,2);
                String srcString = "src/main/java/chessPieces/" + pieceName + colorString + ".png";
                JLabel piece = new JLabel(new ImageIcon(new ImageIcon(srcString).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
                JPanel panel = (JPanel)gui.board.getComponent(i);
                panel.add(piece);
            }
        }
    }
    
    /**
     * if player presses mouse 1 (left button), move the piece through click and drag
     */
    public void mousePressed(MouseEvent e){
        if (e.getButton() == 1) {
            piece = null;
            Component c = board.findComponentAt(e.getX(), e.getY());
            pieceOrigin = c.getParent().getName();

            if (c instanceof JPanel)
                return;

            Point parentLocation = c.getParent().getLocation();
            xAdjustment = parentLocation.x - e.getX();
            yAdjustment = parentLocation.y - e.getY();
            piece = (JLabel) c;
            piece.setLocation(e.getX() + xAdjustment, e.getY() + yAdjustment);
            piece.setSize(piece.getWidth(), piece.getHeight());
            pane.add(piece, JLayeredPane.DRAG_LAYER);
        }
    }

    /**
     * sets piece location for mouse dragging action
     */
    public void mouseDragged(MouseEvent me) {
        if (piece == null) return;
        piece.setLocation(me.getX() + xAdjustment, me.getY() + yAdjustment);
    }

    /**
     * adds piece to the new board position after mouse release and updates moveMade
     */
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            if (piece == null) return;

            piece.setVisible(false);
            Component c = board.findComponentAt(e.getX(), e.getY());

            if (c instanceof JLabel) {
                pieceDestination = c.getParent().getName();
                Container parent = c.getParent();
                parent.remove(0);
                parent.add(piece);
                moveMade = true;
            } else {
                pieceDestination = c.getName();
                Container parent = (Container) c;
                parent.add(piece);
                moveMade = true;
            }

            piece.setVisible(true);
        }
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
    
    //main
    public static void main(String[] args) {
        //creates player and new game
        PlayerUser white = new PlayerUser("p1", "1000");
        PlayerUser black = new PlayerUser("p2", "1000");
        Game game = new Game("Standard", white, black);
        //creates new gui frame
        GameGui frame = new GameGui(game);
        //remove frame on close
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
        //packs frame to the content to optimize space usage
        frame.pack();
        //gui is not resizable
        frame.setResizable(false);
        //center
        frame.setLocationRelativeTo( null );
        //clears the gui in case any pieces are present
        clearGui(frame, frame.pieceDestination);
        //update the gui with new pieces
        updateGui(game, frame);
        //make the piece frame visible
        frame.setVisible(true);
        //standard display mode
        game.standardDisplay();
        //undo
        originator.set(game.board);
        caretaker.addMementoUndo(originator.storeInMemento());
        
        //welcome screen

        JFrame frame2 = new JFrame("Chess");
        frame2.setDefaultCloseOperation(HIDE_ON_CLOSE);

        JPanel contentPanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        frame2.setContentPane(contentPanel);

        JLabel welcomeLabel = new JLabel("<html><br><h1 style=\"text-align:center;\">Welcome to Chess!</h1>" +
                "<br>Drag and drop pieces to play and use the buttons to the right to undo or redo a move. <br> If " +
                "you want to play a 'real' game with no undo, just close this window!" +
                "<h4 style=\"text-align:center;\">Have fun!</h4></html>");
        frame2.getContentPane().add(welcomeLabel, BorderLayout.NORTH);
        //undo and redo buttons
        JButton undoButton = new JButton("Undo");
        undoButton.setBounds(50,100,100,50);
        JButton redoButton = new JButton("Redo");
        redoButton.setBounds(50,100,100,50);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener(){
            /**
             * When clicked, the undo button will add a memento to the redo stack and revert the current board state
             * to the last move.
             * @param e when Clicked
             */
            public void actionPerformed(ActionEvent e){
                if(caretaker.undoStack().size() > 1) {

                    caretaker.addMementoRedo(caretaker.getMementoUndo());
                    Memento tempMem = caretaker.undoStack().peek();
                    game.board.copy(originator.restoreFromMemento(tempMem));

                    redoButton.setEnabled(true);

                    game.standardDisplay();
                    clearGui(frame, frame.pieceDestination);
                    updateGui(game, frame);
                    frame.pieceDestination = frame.pieceOrigin;
                    frame.setVisible(true);

                }
                if(caretaker.undoStack().size() <= 1) {
                    undoButton.setEnabled(false);
                }
            }
        });

        redoButton.addActionListener(new ActionListener(){
            /**
             * When clicked, the redo button will revert the last undo to the board state before the undo.
             * @param e Clicked.
             */
            public void actionPerformed(ActionEvent e){
                if (!caretaker.redoStack().empty()){
                    Memento tempMem = caretaker.getMementoRedo();
                    game.board.copy(originator.restoreFromMemento(tempMem));
                    caretaker.addMementoUndo(tempMem);

                    game.standardDisplay();
                    clearGui(frame, frame.pieceDestination);
                    updateGui(game, frame);
                    frame.pieceOrigin = frame.pieceDestination;
                    frame.setVisible(true);
                    undoButton.setEnabled(true);
                }
                if (caretaker.redoStack().empty()) {
                    redoButton.setEnabled(false);
                }
            }
        });
        //adds buttons and packs, makes frame visible
        frame2.getContentPane().add(undoButton);
        frame2.getContentPane().add(redoButton);
        frame2.setSize(500, 500);
        frame2.pack();
        frame2.setLocationRelativeTo(frame);
        frame2.setVisible(true);

        //game loop
        //init movement
        String moveString = frame.pieceOrigin + "," + frame.pieceDestination;
        while(!moveString.equals("end")) {
            frame.moveMade = false;
            //updates until a move is made
            while (!frame.moveMade) {
                moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            }
            //captures the movement string
            moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            String [] orDest = moveString.split(",");
            int origin = Integer.parseInt(orDest[0]);
            int destination = Integer.parseInt(orDest[1]);
            //makes move if legal, checks for game end and adds move to undo and redo
            if (game.board.checkMoveLegal(origin, destination)) {
                int moveResult = game.board.makePlayerMove(origin, destination);
                if (moveResult == 0) {
                    undoButton.setEnabled(true);
                    originator.set(game.board);
                    caretaker.addMementoUndo(originator.storeInMemento());
                    caretaker.clearRedo();

                }
                else if (moveResult == 2){
                    JOptionPane.showMessageDialog(frame,
                            "Checkmate!",
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
            //clears the gui and updates it, makes it visible again and cont loop
            game.standardDisplay();
            clearGui(frame, frame.pieceDestination);
            updateGui(game, frame);
            frame.setVisible(true);
        }
    }

    /**
     * Run a GameGui when PlayerUser sends a request to play the chess game
     * @param white: PlayerUser who moves first
     * @param black: PlayerUser who moves next
     * @param database: Database
     * @throws UsernameDoesNotExist
     */
    public static void run(PlayerUser white, PlayerUser black, Database database) throws UsernameDoesNotExist {

        Game game = new Game("Standard", white, black);

        GameGui frame = new GameGui(game);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        clearGui(frame, frame.pieceDestination);
        updateGui(game, frame);
        frame.setVisible(true);

        game.standardDisplay();
        originator.set(game.board);
        caretaker.addMementoUndo(originator.storeInMemento());

        JFrame frame2 = new JFrame("Chess");
        frame2.setDefaultCloseOperation(HIDE_ON_CLOSE);

        JPanel contentPanel = new JPanel();

        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        frame2.setContentPane(contentPanel);

        JLabel welcomeLabel = new JLabel("<html><br><h1 style=\"text-align:center;\">Welcome to Chess!</h1>" +
                "<br>Drag and drop pieces to play and use the buttons to the right to undo or redo a move. <br> If " +
                "you want to play a 'real' game with no undo, just close this window!" +
                "<h4 style=\"text-align:center;\">Have fun!</h4></html>");
        frame2.getContentPane().add(welcomeLabel, BorderLayout.NORTH);
        JButton undoButton = new JButton("Undo");
        undoButton.setBounds(50, 100, 100, 50);
        JButton redoButton = new JButton("Redo");
        redoButton.setBounds(50, 100, 100, 50);
        undoButton.setEnabled(false);
        redoButton.setEnabled(false);
        undoButton.addActionListener(new ActionListener() {
            /**
             * When clicked, the undo button will add a memento to the redo stack and revert the current board state
             * to the last move.
             *
             * @param e when Clicked
             */
            public void actionPerformed(ActionEvent e) {
                if (caretaker.undoStack().size() > 1) {

                    caretaker.addMementoRedo(caretaker.getMementoUndo());
                    Memento tempMem = caretaker.undoStack().peek();
                    game.board.copy(originator.restoreFromMemento(tempMem));

                    redoButton.setEnabled(true);

                    game.standardDisplay();
                    clearGui(frame, frame.pieceDestination);
                    updateGui(game, frame);
                    frame.pieceDestination = frame.pieceOrigin;
                    frame.setVisible(true);

                }
                if (caretaker.undoStack().size() <= 1) {
                    undoButton.setEnabled(false);
                }
            }
        });

        redoButton.addActionListener(new ActionListener() {
            /**
             * When clicked, the redo button will revert the last undo to the board state before the undo.
             *
             * @param e Clicked.
             */
            public void actionPerformed(ActionEvent e) {
                if (!caretaker.redoStack().empty()) {
                    Memento tempMem = caretaker.getMementoRedo();
                    game.board.copy(originator.restoreFromMemento(tempMem));
                    caretaker.addMementoUndo(tempMem);

                    game.standardDisplay();
                    clearGui(frame, frame.pieceDestination);
                    updateGui(game, frame);
                    frame.pieceOrigin = frame.pieceDestination;
                    frame.setVisible(true);
                    undoButton.setEnabled(true);
                }
                if (caretaker.redoStack().empty()) {
                    redoButton.setEnabled(false);
                }
            }
        });

        frame2.getContentPane().add(undoButton);
        frame2.getContentPane().add(redoButton);
        frame2.setSize(500, 500);
        frame2.pack();
        frame2.setLocationRelativeTo(frame);
        frame2.setVisible(true);

        String moveString = frame.pieceOrigin + "," + frame.pieceDestination;
        while (!moveString.equals("end")) {
            frame.moveMade = false;
            while (!frame.moveMade) {
                moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            }
            moveString = frame.pieceOrigin + "," + frame.pieceDestination;
            String[] orDest = moveString.split(",");
            int origin = Integer.parseInt(orDest[0]);
            int destination = Integer.parseInt(orDest[1]);
            if (game.board.checkMoveLegal(origin, destination)) {
                int moveResult = game.board.makePlayerMove(origin, destination);
                if (moveResult == 0) {
                    undoButton.setEnabled(true);
                    originator.set(game.board);
                    caretaker.addMementoUndo(originator.storeInMemento());
                    caretaker.clearRedo();

                } else if (moveResult == 2) {
                    JOptionPane.showMessageDialog(frame,
                            "Checkmate!",
                            "Game End",
                            JOptionPane.PLAIN_MESSAGE);
                    game.endGame(true);
                } else if (moveResult == 3) {
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
