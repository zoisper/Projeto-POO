import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Controller {
    State state;

    public Controller(State state){
        this.state = state;
    }

    public  void run() {

        int option = -1;
        Menu menu = new Menu(new String[]{"Fazer Jogo", "Ver Jogadores", "Ver Equipas","Criar Jogador", "Criar Equipa", "Transferir Jogador", "Gravar Estado", "Ler Estado"});
        do {
            menu.run();
            option = menu.getOption();
            switch (option) {
                case 1:
                    break;
                case 2:
                    showPlayers();
                    break;
                case 3:
                    showTeams();
                    break;
                case 4:
                    createPlayer();
                    break;
                case 5:
                    //StateController.save(state);
                    break;
                case 6:
                    transferPalyer();
                    break;
            }
        } while (option != 0) ;
    }



    public  void showPlayers(){
        Iterator<FootballPlayer> iteratorPlayer = this.state.getPlayers().values().iterator();
        IO.newLine();
        IO.showPlayersDataBase(iteratorPlayer);
        IO.pressEnter();
    }

    public  void showTeams(){
        List<String> teams = new ArrayList<>(this.state.getTeams().keySet());
        Menu menu = new Menu(teams, "*** Selecione Equipa ***");
        int option = -1;
        do {
            menu.run();
            option = menu.getOption();
            option--;
            String teamName = teams.get(option);
            FootballTeam team = this.state.getTeam(teamName);
            IO.newLine();
            IO.showTeam(team);
            IO.pressEnter();

        }while (option != 0);

    }

    public  void createPlayer(){
        String name = IO.chooseName();
        Position position = IO.choosePosition();
        int number = IO.chooseNumber();
        int speed = IO.chooseAbility("Velocidade");
        int stamina = IO.chooseAbility("Resistencia");
        int agility = IO.chooseAbility("Agilidade");
        int jumping = IO.chooseAbility("Impulsao");
        int heading = IO.chooseAbility("Jogo de Cabeca");
        int finishing = IO.chooseAbility("Capaciadade de Remate");
        int passing = IO.chooseAbility("Capacidade de Passe");
        int elasticity = 0;
        int recovery = 0;
        int crossing = 0;
        if(position == Position.GOALKEEPER)
            elasticity = IO.chooseAbility("Elasticidade");
        if(position == Position.MIDFIELDER)
            recovery = IO.chooseAbility("Recuperacao");
        if(position == Position.WINGER)
            recovery = IO.chooseAbility("Cruzamento");

        String team = "Sem Equipa";

        switch (position){
            case GOALKEEPER:
                state.addPlayer(new GoalKeeper(name, number, speed, stamina, agility, jumping, heading, finishing, passing, elasticity, team, new ArrayList<String>()));
                break;
            case DEFENDER:
                state.addPlayer(new Defender(name, number, speed, stamina, agility, jumping, heading, finishing, passing, team, new ArrayList<String>()));
                break;
            case WINGER:
                state.addPlayer(new Winger(name, number, speed, stamina, agility, jumping, heading, finishing, passing, crossing, team, new ArrayList<String>()));
                break;
            case MIDFIELDER:
                state.addPlayer(new MidFielder(name, number, speed, stamina, agility, jumping, heading, finishing, passing, recovery, team, new ArrayList<String>()));
                break;
            case STRIKER:
                state.addPlayer(new Striker(name, number, speed, stamina, agility, jumping, heading, finishing, passing, team, new ArrayList<String>()));
                break;
        }
    }

    public void transferPalyer(){
        String player = selectPlayer();
        String team = selectTeam();
        state.transferPlayer(player,team);
    }

    public String selectPlayer(){
        Iterator<Map.Entry<String, FootballPlayer>> iteratorPlayers = state.getPlayers().entrySet().iterator();
        int option = IO.selectPlayer(iteratorPlayers);
        iteratorPlayers = state.getPlayers().entrySet().iterator();
        Map.Entry<String,FootballPlayer> mapEntryPlayer = null;
        while(option>0){
            mapEntryPlayer = iteratorPlayers.next();
            option--;
        }
        if(mapEntryPlayer == null)
            return null;
        return mapEntryPlayer.getKey();
    }

    public  String selectTeam(){
        Iterator<Map.Entry<String, FootballTeam>> iteratorTeams = state.getTeams().entrySet().iterator();
        int option = IO.selectTeam(iteratorTeams);
        iteratorTeams = state.getTeams().entrySet().iterator();
        Map.Entry<String,FootballTeam> mapEntryTeam = null;
        while(option>0){
            mapEntryTeam = iteratorTeams.next();
            option--;
        }
        if(mapEntryTeam == null)
            return null;
        return mapEntryTeam.getKey();
    }
}









