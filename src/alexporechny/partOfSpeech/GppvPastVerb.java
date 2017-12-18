package alexporechny.partOfSpeech;

/**
 * Глагол прошедшего времени ГППВ - ГП - 72
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class GppvPastVerb extends WordForm {

    private final int lico;

    public GppvPastVerb(int myIndex, int intRod, int intChis, int lico) {
        super(myIndex, intRod, intChis);
        this.lico = lico;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Глагол прошедшего времени";
        indexPtOfSp = 72;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        switch (child.indexPtOfSp) {
            case 1:
                return Math.abs(child.myIndex - myIndex) <= 4 && ((child.intPad == 1 || child.intPad == 4) ? (!((SNoun) child).havePreposition()) : true);
            case 17:
                return Math.abs(myIndex - child.myIndex) <= 4;
            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return true;
    }

    /**
     * @return лицо : 0 - неопределено, 1 - 1-ое, 2 - 2-ое, 3 - 3-ье
     */
    public int getLico() {
        return lico;
    }
}
