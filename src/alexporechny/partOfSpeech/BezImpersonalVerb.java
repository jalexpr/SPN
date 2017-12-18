package alexporechny.partOfSpeech;

/**
 * Безличный глагол БезЛ - 6
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class BezImpersonalVerb extends WordForm {

    private final int indexVrem;

    public BezImpersonalVerb(int myIndex, int intRod, int intChis, int indexVrem) {
        super(myIndex, intRod, intChis);
        this.indexVrem = indexVrem;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Безличный глагол";
        indexPtOfSp = 6;
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
}
