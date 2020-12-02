//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class mainmenu extends GuiPanel{
//
//}
//    private Font titleFont = Game.main.deriveFont(100f);
//    private Font creatorFont = Game.main.deriveFont(24f);
//    private String title = "2048";
//    private String creator = "by me";
//    private int buttonWidth = 220;
//    private int spacing = 90;
//    private int buttonheight = 60;
//
//    public mainmenu() {
//        super();
//
//        GuiButton playButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
//        GuiButton scoreButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, playButton.getY() + spacing, buttonWidth, buttonHeight);
//        GuiButton quitButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, leaderboardButton.getY() + spacing, buttonWidth, buttonHeight);
//
//        playButton.setText("PLAY");
//        scoresButton.setText("SCORES");
//        quitButton.setText("QUIT");
//
//        playButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent arg0) {
//                GuiScreen.getlnstance().setCurrentPanel("play");
//            }
//        })