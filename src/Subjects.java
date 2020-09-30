public class Subjects {

    public String subjects(String sub) {
        String subj = sub.toUpperCase();
        switch (subj) {
            case "AVM" :
                return  "Aviation Management";
            case "FE" :
                return  "Engineering Facultyie";
            case "LAW" :
            case "HUK" :
                return "Law";
            case "CS" :
                return  "Computer Science";
            case  "EE" :
                return  "Electrical and Electronics Engineering";
        }
        return "";
    }
}
