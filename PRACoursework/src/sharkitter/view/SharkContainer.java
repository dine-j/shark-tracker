package sharkitter.view;

import api.jaws.Ping;
import api.jaws.Shark;
import sharkitter.controller.FavouriteController;
import sharkitter.model.FavouriteSharks;

import java.awt.*;
import java.util.HashMap;
import javax.swing.*;

public class SharkContainer extends JPanel implements Comparable<SharkContainer> {

    private FavouriteController favouriteController;
    private JButton followButton;
    private HashMap<String,String> sharkdetails;
    private Shark shark;
    private FavouriteSharks favouriteSharks;

    /**
     * Class constructor: provides a JPanel containing all the details of a Shark when the following parameters
     * are given:
     * Display all information concerning the Sharks that fit the chosen fields for the search.
     * @param foundShark	A Shark matching the chosen criteria.
     * @param lastPing	The last Ping for the matching Shark.
     */
    public SharkContainer(Shark foundShark, Ping lastPing, FavouriteSharks favouriteSharks){
        sharkdetails = new HashMap<>();
        populateSharkDetails(foundShark,lastPing);
        setLayout(new BorderLayout());
        setName(foundShark.getName());

        favouriteController = new FavouriteController(this, favouriteSharks);
        shark = foundShark;

        add(createSharkFeaturesTable(shark), BorderLayout.NORTH);

        add(createSharkDescriptionText(shark), BorderLayout.WEST);

        add(createSharkTrackOptions(lastPing), BorderLayout.SOUTH);

        setSize(new Dimension(400,200));
        setVisible(true);
    }

    private void populateSharkDetails(Shark foundShark,Ping ping){
        sharkdetails.put("name",foundShark.getName());
        sharkdetails.put("gender",foundShark.getGender());
        sharkdetails.put("stageoflife",foundShark.getStageOfLife());
        sharkdetails.put("taglocation",foundShark.getTagLocation());
        sharkdetails.put("lastping",ping.getTime());
    }

    public String getSharkDate(){
        return sharkdetails.get("lastping");
    }
    /**
     * Create and display the description of a matching Shark.
     * @param foundShark	A Shark matching the chosen criteria.
     * @return	A JPanel containing all relevant information (description).
     */
    private JPanel createSharkDescriptionText(Shark foundShark) {
        JPanel descriptionPanel = new JPanel();

        descriptionPanel.add(new JLabel("Description: \n\n"));
        descriptionPanel.add(new JScrollPane(new JTextArea(foundShark.getDescription())));
        setVisible(true);

        return descriptionPanel;
    }
    /**
     * Create and display the last ping of a given Shark and the option to follow it.
     * @param lastPing	Object containing information about the last point of contact with a given shark.
     * @return	A JPanel containing all relevant information and a follow button.
     */
    private JPanel createSharkTrackOptions(Ping lastPing) {
        JPanel pingPanel = new JPanel(new BorderLayout());

        JLabel pingLabel = new JLabel("Last ping: " + lastPing.getTime());

        followButton = new JButton("Follow");

        followButton.addActionListener(favouriteController);

        pingPanel.add(pingLabel, BorderLayout.CENTER);
        pingPanel.add(followButton, BorderLayout.EAST);
        setVisible(true);

        return pingPanel;
    }

    /**
     * Create a table to display specific information about a Shark (name, gender, stage of life, species, length and weight).
     * @param foundShark	A Shark matching the chosen criteria.
     * @return	a JPanel containing all relevant information (name, gender, satge of life, species, length and weight).
     */
    private JPanel createSharkFeaturesTable(Shark foundShark) {
        JPanel sharkFeatures = new JPanel(new GridLayout(6, 2));

        JLabel nameLabel = new JLabel("Name: ");
        JLabel genderLabel = new JLabel("Gender: ");
        JLabel stageOfLifeLabel = new JLabel("Stage of Life: ");
        JLabel specieLabel = new JLabel("Species: ");
        JLabel lengthLabel = new JLabel("Length: ");
        JLabel weightLabel = new JLabel("Weight: ");

        JLabel sharkName = new JLabel(foundShark.getName());
        JLabel sharkGender = new JLabel(foundShark.getGender());
        JLabel sharkStageOfLife = new JLabel(foundShark.getStageOfLife());
        JLabel sharkSpecie = new JLabel(foundShark.getSpecies());
        JLabel sharkLength = new JLabel(foundShark.getLength());
        JLabel sharkWeight = new JLabel(foundShark.getWeight());

        sharkFeatures.add(nameLabel);
        sharkFeatures.add(sharkName);
        sharkFeatures.add(genderLabel);
        sharkFeatures.add(sharkGender);
        sharkFeatures.add(stageOfLifeLabel);
        sharkFeatures.add(sharkStageOfLife);
        sharkFeatures.add(specieLabel);
        sharkFeatures.add(sharkSpecie);
        sharkFeatures.add(lengthLabel);
        sharkFeatures.add(sharkLength);
        sharkFeatures.add(weightLabel);
        sharkFeatures.add(sharkWeight);
        setVisible(true);

        return sharkFeatures;
    }

    /**
     * Getter of the corresponding shark
     * @return  The Shark object displayed by this container
     */
    public Shark getShark() {
        return shark;
    }

    /**
     * Updates a button with the corresponding text
     * @param text  New text for the JButton
     */
    public void updateFollowButton(String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                followButton.setText(text);
            }
        });
    }

    @Override
    public int compareTo(SharkContainer anotherSharkContainer) {
        return getSharkDate().compareTo(anotherSharkContainer.getSharkDate());
    }
}