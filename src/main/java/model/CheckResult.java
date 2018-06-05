package model;

public class CheckResult {
    public Field[] fields;
    public CheckResult(Field... fields) {
        this.fields = fields;
    }

    public boolean isComplete() {
        if (fields[0].getSymbolValue().isEmpty())
            return false;

        return !fields[0].getSymbolValue().equals(" ") && (fields[0].getSymbolValue().equals(fields[1].getSymbolValue())
                && fields[0].getSymbolValue().equals(fields[2].getSymbolValue())
                && fields[0].getSymbolValue().equals(fields[3].getSymbolValue())
                && fields[0].getSymbolValue().equals(fields[4].getSymbolValue()));
    }
}
