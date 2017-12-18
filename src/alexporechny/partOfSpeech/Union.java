package alexporechny.partOfSpeech;

/**
 * союз
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class Union extends WordForm {

    public Union(int i, int GetRodJ, int GetPadezhJ, int GetChisloJ) {
        super(i, GetRodJ, GetPadezhJ, GetChisloJ);
        initialization();
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        return false;
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Union";
        indexPtOfSp = 19;
    }

    @Override
    public boolean isMainPS() {
        return false;
    }

    public boolean isSubordinate() {
        switch (realForm) {
            case "потому":
            case "оттого":
            case "благодаря":
            case "вследствие":
            case "чтобы":
            case "когда":
            case "если":
            case "как":
            case "несмотря":
            case "что":
            case "для":
            case "хотя":
                return true;
            default:
                return false;

        }
    }
}
