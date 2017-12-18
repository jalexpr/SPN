package alexporechny.partOfSpeech;

/**
 * Существительное - С - 1
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class SNoun extends WordForm {

    private final boolean isPronoun;
    private boolean havePreposition = false;

    //Конструктор существительного
    public SNoun(int myIndex, int intRod, int intPad, int intChis) {
        this(myIndex, intRod, intPad, intChis, false);
    }

    //конструктор возможно сущ или местоимения
    public SNoun(int myIndex, int intRod, int intPad, int intChis, boolean isPronoun) {
        super(myIndex, intRod, intPad, intChis);
        this.isPronoun = isPronoun;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Существительное";
        indexPtOfSp = 1;
    }

    //Поиск связей сущ.+
    //Причем нужно заранее передать в sdk предложение
    @Override
    public boolean isRelationsSwitch(WordForm child) {
        int buf = child.myIndex - myIndex;
        switch (child.indexPtOfSp) {
            //+существитльеное
            case 1:
                return (!((SNoun) child).havePreposition) && buf >= 0 && buf <= 2
                        && (child.intPad == 2 || ((intPad == 3 || intPad == 5) && child.intPad != 1));

            //+прилогательное
            case 5:
                return Math.abs(buf) <= 2 && intChis == child.intChis && (intChis == 1 ? intRod == child.intRod : true) && intPad == child.intPad;
            //+причестие (проверить!)
            case 251:
                return intChis == child.intChis && (intChis == 1 ? intRod == child.intRod : true) && intPad == child.intPad;
            //+порядковые числительные
            case 12:
                return true;
            //+наречия
            case 13:
            case 14:
            case 15:
                return child.myIndex - myIndex == 2;
            //+предлог !!! нужно проверить!
            case 17:
                return !this.havePreposition && buf >= 0 && buf <= 3 && (child.intPad == 2 || intPad == 3 || intPad == 4 || intPad == 5);
            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return true;
    }

    /**
     * @return возвращает true - если словоформа имеет предлог, false - если
     * словоформа не имеет предлога
     */
    public boolean havePreposition() {
        return havePreposition;
    }

    /**
     * @param bl: true - если словоформа имеет предлог, false - если словоформа не имеет
     * предлога
     */
    public void setHavePreposition(boolean bl) {
        havePreposition = bl;
    }

    /**
     * @return возврящает true - если словоформа существительное-местоимение
     */
    public boolean getIsPronoun() {
        return isPronoun;
    }
}
