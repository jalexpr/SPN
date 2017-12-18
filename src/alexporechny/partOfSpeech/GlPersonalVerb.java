package alexporechny.partOfSpeech;

/**
 * Личный глагол ГЛ - 7
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class GlPersonalVerb extends WordForm {

    private final int indexVrem;
    private final int lico;

    public GlPersonalVerb(int myIndex, int intRod, int intChis, int indexVrem, int lico) {
        super(myIndex, intRod, intChis);
        this.indexVrem = indexVrem;
        this.lico = lico;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Личный глагол";
        indexPtOfSp = 7;
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

    /**
     * @return Возвращает время глагола: 0 - неопределено 1 - действие 2 -
     * причастие 3 - деепричастие
     */
    public int getIndexVrem() {
        return indexVrem;
    }

    @Override
    public boolean isMainPS() {
        return true;
    }

    /**
     * @return лицо
     */
    public int getLico() {
        return lico;
    }
}
