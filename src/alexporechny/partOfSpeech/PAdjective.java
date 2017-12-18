package alexporechny.partOfSpeech;

/**
 * Прилогательное - П - 5
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class PAdjective extends WordForm {

    private boolean isPronoun;

    public PAdjective(int myIndex, int intRod, int intPad, int intChis) {
        this(myIndex, intRod, intPad, intChis, false);
    }

    public PAdjective(int myIndex, int intRod, int intPad, int intChis, boolean isPronoun) {
        super(myIndex, intRod, intPad, intChis);
        this.isPronoun = isPronoun;
        initialization(isPronoun);
    }

    @Override
    protected void initialization() {
        initialization(false);
    }

    protected void initialization(boolean isPronoun) {
        this.isPronoun = isPronoun;
        namePtOfSp = "П";
        indexPtOfSp = 5;
    }

    @Override
    public boolean isMainPS() {
        return false;
    }

    /**
     * @return возврящает true - если словоформа прилогательное-местоимение
     */
    public boolean getIsPronoun() {
        return isPronoun;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        return false;
    }

}
