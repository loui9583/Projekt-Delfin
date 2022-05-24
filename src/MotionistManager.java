import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class MotionistManager {
    GUI gui = new GUI();
    ArrayList<Member> memberList = new ArrayList<>();

    public MotionistManager() {
    }

    static void updateInfoHjaelp(PrintStream out, Member member) {
        out.print(member.getNumber());
        out.print(";");
        out.print(member.getName());
        out.print(";");
        out.print(member.getAge());
        out.print(";");
        out.print(member.getPassivAktiv());
        out.print(";");
        out.print(member.getJuniorSenior());
        out.print(";");
        out.print(member.getKontingent());
        out.print(";");
        out.print(member.getPaymentDueDate());
        out.print(";");
    }

    void addMember() throws FileNotFoundException, ParseException {
        System.out.println("Enter name.");
        String name = gui.getString();
        System.out.println("Enter age:");
        int age = gui.getInt();
        System.out.println("Enter member status(passive/active). Use P or A");
        String passiveActive = "";
        while (!passiveActive.equals("P") && !passiveActive.equals("A")) {
            passiveActive = gui.getString().toUpperCase(Locale.ROOT);
            if (!passiveActive.equals("P") && !passiveActive.equals("A")) System.out.println("Please write 'P' or 'A'");
        }
        String paymentDueDate = ID.createPaymentDate();
        memberList.add(new Member(ID.getID(), name, age, passiveActive, paymentDueDate, "ikkeBetalt"));
        updateInfo(-1);
    }

    void updateInfo(int x) throws FileNotFoundException {

        PrintStream out = new PrintStream(("MembersInfo\\casualMembers.csv"));
        for (Member member : memberList) {

            updateInfoHjaelp(out, member);
            if (member.getNumber() == x) {
                out.print("betalt");
                member.setBetalt("betalt");
            }
            if (member.getNumber() != x) out.print(member.getBetalt());
            out.print("\n");
        }
    }

    public void loadMemberFileM() throws FileNotFoundException, ParseException {
        Scanner fileScanner = new Scanner(new File("MembersInfo\\casualMembers.csv"));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Scanner input = new Scanner(line).useDelimiter(";").useLocale(Locale.ENGLISH);
            int number = input.nextInt();
            String name = input.next();
            int age = input.nextInt();
            String aktivPassiv = input.next();
            input.next();
            input.next();
            String paymentDueDate = input.next();
            String betalt = input.next();
            memberList.add(new Member(number, name, age, aktivPassiv, paymentDueDate, betalt));
        }
    }

    void visRestance() {
        Date currentDate = new Date();
        System.out.println(memberList.get(0).getPaymentDueDate());
        for (Member member : memberList) {
            if (member.getBetalt().equals("ikkeBetalt") && currentDate.compareTo(member.getDueDate()) > 0) {
                System.out.println(member.getName() + " er i restance. ID nr:" + member.getNumber());
            }
        }
    }
}