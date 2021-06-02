import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Controller {
    State state;

    public Controller(State state){
        this.state = state;
    }

    public  void run() {

        int option = -1;
        Menu menu = new Menu(new String[]{"Fazer Jogo", "Ver Resultados", "Ver Jogadores", "Ver Equipas","Criar Jogador", "Criar Equipa", "Transferir Jogador", "Gravar Estado", "Ler Estado"});
        do {
            menu.run();
            option = menu.getOption();
            switch (option) {
                case 1:
                    try{
                        IO.newLine();
                        this.makeGame();
                    }catch (NoTeamsException e){
                        IO.message(e.getMessage());
                    }
                    break;
                case 2:
                    IO.newLine();
                    this.showGames();
                    break;

                case 3:
                    IO.newLine();
                    this.showPlayers();
                    break;
                case 4:
                    IO.newLine();
                    this.showTeams();
                    break;
                case 5:
                    IO.newLine();
                    this.createPlayer();
                    break;
                case 6:
                    IO.newLine();
                    this.createTeam();
                    break;
                case 7:
                    IO.newLine();
                    transferPlayer();
                    break;
                case 8:
                    IO.newLine();
                    this.save();
                    break;
                case 9:
                    IO.newLine();
                    this.load();
                    break;
            }
        } while (option != 0) ;
    }

    public void makeGame () throws NoTeamsException{
        Predicate<FootballTeam> p = FootballTeam::isComplete;
        if (this.state.getTeams().values().stream().filter(p).count() <1)
            throw new NoTeamsException();

        Menu menu = new Menu(new String[]{"Jogo Completo", "Simular Resultado"},"***Tipo de Jogo***");
        int option = -1;

        do{
            menu.run();
            option = menu.getOption();
        }while (option <0 || option>2);

        if(option !=0){

            String teamHome = this.selectTeam(p);
            String teamAway = this.selectTeam(p);
            FootballTeam home = this.state.getTeam(teamHome);
            FootballTeam away = this.state.getTeam(teamAway);
            FootballMatch fm = new FootballMatch(home, away);
            MatchController mc = new MatchController(fm);

            if (option == 1)
                mc.run();
            else
                mc.calculateResult();
            this.state.addGame(mc.getMatch());

        }



    }

    public void showGames (){
        List<FootballMatch> games = this.state.getGames();
        IO.showGames(games.iterator());
    }



    public  void showPlayers(){
        Iterator<FootballPlayer> iteratorPlayer = this.state.getPlayers().values().iterator();
        IO.newLine();
        IO.showPlayersDataBase(iteratorPlayer);
        IO.pressEnter();
    }

    public void showTeams(){
        List<String> teams = new ArrayList<>(this.state.getTeams().keySet());
        Menu menu = new Menu(teams, "*** Selecione Equipa ***");
        int option = -1;
        do {
            menu.run();
            option = menu.getOption();
            if(option !=0) {
                String teamName = teams.get(option-1);
                FootballTeam team = this.state.getTeam(teamName);
                IO.newLine();
                IO.showTeam(team);
                IO.pressEnter();
            }

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

    public void createTeam(){
        String name = IO.chooseName();
        FootballTeam team = new FootballTeam();
        team.setName(name);
        state.addTeam(team);

    }

    public void transferPlayer(){
        Iterator<Map.Entry<String, FootballPlayer>> iteratorPlayers = state.getPlayers().entrySet().iterator();
        String player = IO.selectPlayer(iteratorPlayers);
        String team = selectTeam();
        state.transferPlayer(player,team);
    }


    public String selectTeam(){
        Iterator<Map.Entry<String, FootballTeam>> iteratorTeams = state.getTeams().entrySet().iterator();
        String option = IO.selectTeam(iteratorTeams);
        return option;
    }

    public String selectTeam(Predicate<FootballTeam> p){
        Iterator<Map.Entry<String, FootballTeam>> iteratorTeams = state.getTeams().entrySet().stream().filter(team -> p.test(team.getValue())).iterator();
        String option = IO.selectTeam(iteratorTeams);
        return option;
    }





    public void save(){
        String fileName = IO.getFileName();
        try{
            this.state.save(fileName);
            IO.message("Gravado Com Sucesso");

        }catch (IOException e){
            IO.message("Ficheiro Não Encontrado");
        }
    }

    public void load(){
        String fileName = IO.getFileName();
        try{
            this.state = State.load(fileName);
            IO.message("Carregado Com Sucesso");
        }catch (IOException | ClassNotFoundException e){
            IO.message("Ficheiro Não Encontrado");
        }
    }

}









