package alexporechny.partOfSpeech;

/**
 * Инфинитив ГИ - 61
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class GiInfinitive extends WordForm {

    public GiInfinitive(int myIndex) {
        super(myIndex, 0, 0);
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Инфинитив";
        indexPtOfSp = 61;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        switch (child.indexPtOfSp) {
            case 1:
                return Math.abs(child.myIndex - myIndex) <= 4 && ((child.intPad == 1 || child.intPad == 4) ? (!((SNoun) child).havePreposition()) : true);
            case 7:
                return myIndex - child.myIndex == 1;
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
