package alexporechny.partOfSpeech;

/**
 * Причастие - ПГ - 251
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class PgParticiple extends WordForm {

    private final int indexVrem;
    private final boolean intDeist;

    public PgParticiple(int myIndex, int intRod, int intPad, int intChis, int indexVrem, boolean intDeist) {
        super(myIndex, intRod, intPad, intChis);
        this.intDeist = intDeist;
        this.indexVrem = indexVrem;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Причастие";
        indexPtOfSp = 251;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        int buf = child.myIndex - myIndex;
        switch (child.indexPtOfSp) {
            //+сущ
            case 1:
                if (isIntDeist()) {
                    return buf > 0 && buf <= 2 && (intChis != child.intChis || intRod != child.intRod);
                } else {
                    return (child.intPad != 1 && child.intPad != 4 && buf > 0 && buf <= 2)// && (intChis != child.intChis || intRod != child.intRod))
                            || (Math.abs(buf) <= 4 && (child.intPad == 1 || child.intPad == 4) && (!((SNoun) child).havePreposition()));
                }
            case 17:
                return buf > 0 && buf < -2;
            //+наречие
            case 13:
            case 14:
            case 15:

            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return true;
    }
    
    /**
     * @return Возвращает время глагола: 0 - неопределено 1 - действие 2 -
     * причастие 3 - деепричастие
     */
    public int getIndexVrem() {
        return indexVrem;
    }

    /**
     * @return возвращает true - действительное false - страдательное
     */
    public boolean isIntDeist() {
        return intDeist;
    }
}
