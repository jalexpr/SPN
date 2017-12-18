package alexporechny.partOfSpeech;

/**
 * Деепричастие - НГ(Д) 26
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class NgAdverbialParticiple extends WordForm {

    private final int indexVrem;

    public NgAdverbialParticiple(int myIndex, int intRod, int intChis, int indexVrem) {
        super(myIndex, intRod, intChis);
        this.indexVrem = indexVrem;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Деепричастие";
        indexPtOfSp = 26;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        switch (child.indexPtOfSp) {
            case 1:
                return Math.abs(child.myIndex - myIndex) <= 4 && (!((SNoun) child).havePreposition());
            case 17:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return true;
    }

    public int getVrem() {
        return getIndexVrem();
    }

    /**
     * @return Возвращает время глагола: 0 - неопределено 1 - действие 2 -
     * причастие 3 - деепричастие
     */
    public int getIndexVrem() {
        return indexVrem;
    }
}
