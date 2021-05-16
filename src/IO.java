import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class IO {


    public static void showPlayersDataBase(Iterator<FootballPlayer> it){
        FootballPlayer fp = null;
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            fp = it.next();
            sb.append(fp).append("\n");
            if(it.hasNext())
                sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public static void showTeam(FootballTeam team){
        System.out.println(team);
    }


    public  static String chooseName(){
        Scanner sc = new Scanner(System.in);
        String name = null;
        while(name == null){
            System.out.print("Digite Nome: ");
            name = sc.nextLine();
        }
        return name;
    }


    public static int  chooseNumber(){
        Scanner sc = new Scanner(System.in);
        int number = 0;
        do{
            System.out.print("Digite Numero: ");
            try {
                number = sc.nextInt();
            } catch (InputMismatchException e){
                number = 0;
                sc.nextLine();
            }
            if(number <1){
                System.out.println("Numero");
                number = 0;
            }
        }while (number == 0);

        return number;
    }

    public static int chooseAbility(String ability){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("Digite Valor De ").append(ability).append(": ");
        int value = -1;
        do{
            System.out.print(sb.toString());
            try{
                value = sc.nextInt();
            }catch (InputMismatchException e){
                value = -1;
                sc.nextLine();
            }
            if(value <0){
                System.out.println("Valor Invalido");
                value = -1;
            }
        } while( value == -1);

        return value;
    }

    public static Position choosePosition(){
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        sb.append("1: Guarda-Redes:\n");
        sb.append("2: Defesa:\n");
        sb.append("3: Lateral:\n");
        sb.append("4: Medio:\n");
        sb.append("5: Avancado:\n");
        sb.append("Escolha Posicao: ");
        int option = -1;
        do{
            System.out.print(sb.toString());
            try{
                option = sc.nextInt();
            }catch (InputMismatchException e){
                option = -1;
                sc.nextLine();
            }
            if(option <1 || option > 5){
                System.out.println("Valor Invalido");
                option = -1;
            }
        }while (option == -1);
        switch (option){
            case 1:
                return Position.GOALKEEPER;
            case 2:
                return Position.DEFENDER;
            case 3:
                return Position.WINGER;
            case 4:
                return Position.MIDFIELDER;
            case 5:
                return Position.STRIKER;
            default:
                return Position.ND;
        }
    }


    public static int selectPlayer(Iterator<Map.Entry<String, FootballPlayer>> it){
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, FootballPlayer> e;
        int i = 0;
        while (it.hasNext()){
            e = it.next();
            sb.append(i+1).append(". ").append(e.getValue()).append('\n');
            i++;
            if(it.hasNext())
                sb.append("\n");
        }
        Scanner sc = new Scanner(System.in);
        int option = -1;
        System.out.println(i);
        System.out.println(sb.toString());
        while (option == -1){
            System.out.println("Selecione Jogador: ");
            try {
                option = sc.nextInt();
            }
            catch (InputMismatchException exc){
                System.out.println("Valor Invalido");
                option = -1;
                sc.nextLine();
            }
            if(option<1 || option>i){
                System.out.println("Valor Invalido");
                option = -1;
            }
        }
        return option;
    }

    public static int selectTeam(Iterator<Map.Entry<String, FootballTeam>> it){
        StringBuilder sb = new StringBuilder();
        Map.Entry<String, FootballTeam> e;
        int i = 0;
        while (it.hasNext()){
            e = it.next();
            sb.append(i+1).append(". ").append(e.getKey()).append('\n');
            i++;

        }
        Scanner sc = new Scanner(System.in);
        int option = -1;
        System.out.println(sb.toString());
        while (option == -1){
            System.out.println("Selecione Equipa: ");
            try {
                option = sc.nextInt();
            }
            catch (InputMismatchException exc){
                System.out.println("Valor Invalido");
                option = -1;
                sc.nextLine();
            }
            if(option<1 || option>i){
                System.out.println("Valor Invalido");
                option = -1;
            }
        }
        return option;

    }
    public static void newLine(){
        System.out.println();
    }

    public static  void pressEnter(){
        System.out.print("Pressione Enter Para Continuar: ");
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

    public static String getFilame(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite Nome Do Ficheiro.");
        String fileName = sc.nextLine();
        return fileName;
    }







}
