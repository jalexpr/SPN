package alexporechny.partOfSpeech;

/**
 * Краткое прилагательное ГПК - ГП - 71
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class GpkShortAdjective extends WordForm {

    public GpkShortAdjective(int myIndex, int intRod, int intChis) {
        super(myIndex, intRod, intChis);
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Краткое прилагательное";
        indexPtOfSp = 71;
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
}
