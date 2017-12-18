package alexporechny.partOfSpeech;

/**
 * Краткое причастие ГПК - ГП - 73
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class GpkShortParticiple extends WordForm {

    public GpkShortParticiple(int myIndex, int intRod, int intChis) {
        super(myIndex, intRod, intChis);
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Краткое причастие";
        indexPtOfSp = 73;
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
