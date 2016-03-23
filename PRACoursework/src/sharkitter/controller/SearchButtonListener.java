package sharkitter.controller;

import api.jaws.Jaws;
import api.jaws.Ping;
import sharkitter.model.SharkData;
import sharkitter.view.SearchFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchButtonListener implements ActionListener{
    private SearchFrame searchframe;
    private ArrayList<SharkData> listOfSharks;
    private String tracking_range;
    private String gender;
    private String tag_location;
    private String stage_of_life;
    private Jaws jawsApi;

    public SearchButtonListener(SearchFrame searchframe){
        jawsApi = new Jaws("EkZ8ZqX11ozMamO9","E7gdkwWePBYT75KE", true);
        this.searchframe = searchframe;
        listOfSharks = new ArrayList<SharkData>();
        }

    public void actionPerformed(ActionEvent e){
       listOfSharks= updatefromTagLocation(updatefromStageOfLife(updatefromGender(updatefromTrackingRange())));
        searchframe.addSeveralSharkContainersToView(listOfSharks);
    }

    private Map<String,Ping> sortPings(ArrayList<Ping> listOfPings){

        Map<String,Ping> MapOfPings = new HashMap<>();

        for(Ping ping: listOfPings){

            if(MapOfPings.containsKey(ping.getName())){

               if(MapOfPings.get(ping.getName()).getTime().compareTo(ping.getTime()) == -1){
                   MapOfPings.put(ping.getName(),ping);
               }
                continue;
            }
            MapOfPings.putIfAbsent(ping.getName(),ping);
        }
        return MapOfPings;
    }



    private ArrayList<SharkData> updatefromTrackingRange(){
        //1. read selected constraint from combo box

        tracking_range = (String)searchframe.getTracking_range().getSelectedItem();

        //2. get all shark components by tracking range
        if (tracking_range.equals("Last 24 Hours")) {
            for(Ping ping: sortPings(jawsApi.past24Hours()).values()){

               listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Week")) {
            for(Ping ping: sortPings(jawsApi.pastWeek()).values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else if (tracking_range.equals("Last Month")) {
            for(Ping ping: sortPings(jawsApi.pastMonth()).values()){

                listOfSharks.add(new SharkData(jawsApi.getShark(ping.getName()),ping));
            }

        } else {
            System.out.println("SearchButtonListener Error 1 : Invalid ComboBox input");
        }
        return listOfSharks;
    }

    private ArrayList<SharkData> updatefromStageOfLife(ArrayList<SharkData> listofsharks){
        stage_of_life = (String)searchframe.getStage_of_life().getSelectedItem();

        if (stage_of_life!="All"){
            listofsharks=selectSharksByStageofLife(listofsharks,stage_of_life);
        }
        return(listofsharks);
    }

    private ArrayList<SharkData> selectSharksByStageofLife(ArrayList<SharkData> listOfSD, String selectionElement){
        System.out.println("selectionElement: "+selectionElement);
        ArrayList<SharkData> newlistofSD = new ArrayList<SharkData> ();
        for (SharkData sharkData: listOfSD){
           if( jawsApi.getShark(sharkData.getName()).getStageOfLife().equals(selectionElement)){
               System.out.println("got there! selectionelement: " + selectionElement + " :");
               newlistofSD.add(sharkData);
           }
        }
        System.out.println("stageoflife_newlistofPings: "+newlistofSD);
        return newlistofSD;
    }


    private ArrayList<SharkData> updatefromGender(ArrayList<SharkData> listOfSD){
        gender = (String)searchframe.getGender().getSelectedItem();

        if (gender!="All"){
            listOfSD = selectSharksByGender(listOfSD,gender);
        }
        return listOfSD;
    }

    private ArrayList<SharkData>  selectSharksByGender(ArrayList<SharkData>  listOfSD, String selectionElement){
        System.out.println("gender: " +selectionElement);
        ArrayList<SharkData> newlistOfSD = new ArrayList<SharkData> ();
        for (SharkData SharkData: listOfSD){
            if( jawsApi.getShark(SharkData.getName()).getGender().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistOfSD.add(SharkData);
            }
        }
        System.out.println("gender_newlistofPings: "+newlistOfSD);
        return newlistOfSD;
    }


    private ArrayList<SharkData>  updatefromTagLocation(ArrayList<SharkData>  listOfSD){
        tag_location = (String)searchframe.getTag_location().getSelectedItem();

        if (tag_location!="All"){
            listOfSD=selectSharksByTagLocation(listOfSD,tag_location);
        }
        System.out.println(listOfSD);
        return listOfSD;

    }

    private ArrayList<SharkData>  selectSharksByTagLocation(ArrayList<SharkData>  listOfPings, String selectionElement){
        System.out.println("tag location: "+ selectionElement);
        ArrayList<SharkData>  newlistofPings = new ArrayList<SharkData> ();
        for (SharkData SharkData: listOfPings){
            if( jawsApi.getShark(SharkData.getName()).getTagLocation().equals(selectionElement)){
                System.out.println("got there! selectionelement: " + selectionElement + " :");
                newlistofPings.add(SharkData);
            }
        }
        System.out.println("tagloc_newlistofPings: "+newlistofPings);
        return newlistofPings;
    }
}

