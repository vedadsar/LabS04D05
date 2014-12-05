import java.util.Arrays;

public class Mine {
	/**
	 * Funkcija kreira kvadratnu matricu dumenzija "dim"
	 * 
	 * @param dim
	 * @return
	 */
	public static int[][] napraviMatricu(int dim) {

		int[][] matrica = new int[dim][dim];
		return matrica;
	}

	/**
	 * Funkcija ispisuje matricu
	 * 
	 * @param matrica
	 */
	public static void ispisMatrice(int[][] matrica) {

		for (int i = 0; i < matrica.length; i++) {
			System.out.print("|");
			for (int j = 0; j < matrica[i].length; j++) {
				System.out.printf(" %2d |", matrica[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Funkcija vraca random broj od 0 do velicine matrice.
	 * 
	 * @param poc
	 * @param kraj
	 * @return
	 */
	public static int getRandom(int poc, int kraj) {

		int randomBroj = (int) (poc + Math.random() * (kraj - poc));
		return randomBroj;
	}

	/**
	 * Funkcija prima neki niz, zatim upisuje mine u taj niz i vraca novi niz.
	 * 
	 * @param niz
	 * @return
	 */
	public static int[][] rasporediMine(int[][] niz) {

		int brojMina = (int) (niz.length * niz.length * (0.3));

		while (brojMina > 0) {
			int x = getRandom(0, niz.length);
			int y = getRandom(0, niz.length);

			if (niz[x][y] != (-1)) {
				niz[x][y] = -1;
				brojMina -= 1;
			}

		}
		return niz;
	}
/**
 * funkcija prima matricu, i popunjava polja sa brojevimo u odnosu koliko mina oko polja ima. 
 * Funkcija trazi polje gdje je mina i zaatim svakom polju oko njega poveca vrijednost.
 * @param matrica Nova matrica sa popunnjenim vrijednostima.
 * @return
 */
	public static int[][] popuniBrojevima(int[][] matrica) {
        // For petlje koje traze minu ( -1 )
		for (int i = 0; i < matrica.length; i++) {
			for (int j = 0; j < matrica[i].length; j++) {
				
				if (matrica[i][j] == -1) { // Kada nase polje na -1, defnisemo pocetne i krajnje redove i kolone.
				
					int pocI = i - 1; //Pocetak reda I
					int krajI = pocI + 2; // Kraj reda II

					int pocJ = j - 1; // Pocetak kolone J
					int krajJ = pocJ + 2; //Kraj kolone J

					if (pocI < 0) // U slucaju kada je pocetna red manja od 0, mi je postavljamo na 0.
						pocI = 0;

					if (krajI >= matrica.length) // Kada je pocetni red veci od velicine matrice, vrijednost dajemo zadnji red matrice.
						krajI = matrica.length - 1;

					if (pocJ < 0) // Isto kao i za I
						pocJ = 0;

					if (krajJ >= matrica.length) // Isto kao i za I
						krajJ = matrica.length - 1;

					//Dvije for petlje formiraju novu tabelu (3*3) i sva polja koja nisu (-1, da se nebi mina preslikala) povecavaju za 1.
					for (int k = pocI; k <= krajI; k++) {
						for (int n = pocJ; n <= krajJ; n++) {

							if (matrica[k][n] != -1)
								matrica[k][n]++;
						}
					}

				}

			}
		}
		return matrica;
	}
/**
 * Nova matrica radi boljeg vizualnog igranja, popunjena praznim stringovima.
 * @param matrica
 * @return
 */
	public static String[][] stringMatrica(int[][] matrica) {

		String[][] tabela = new String[matrica.length][matrica.length];

		for (int i = 0; i < tabela.length; i++) {
			for (int j = 0; j < tabela[i].length; j++) {
				tabela[i][j] = "";
			}
		}
		return tabela;
	}
/**
 * Funkcija u novu stvorenu matricu koja je prazna, unosi "Broj" sa koordinate X Y koju korisnik odabare. Zatim ispisuje tu matricu.
 * @param tabela nova string matrica
 * @param matrica stara matrica popunjena brojevima
 * @param x koordinate
 * @param y koordinate
 * @return
 */
	public static String[][] igraj(String[][] tabela, int[][] matrica, int x,
			int y) {

		int broj = matrica[x][y];
		tabela[x][y] = "" + broj;

		for (int i = 0; i < tabela.length; i++) {
			System.out.print("|");
			for (int j = 0; j < tabela[i].length; j++) {
				System.out.printf(" %2s |", tabela[i][j]);
			}
			System.out.println();
		}
		System.out.println();

		return tabela;
	}
/**
 * Main funkcija poziva vise funkcija, ali u njoj se nalazi glavni dio. Poziva igru i zavrsava igru !
 * @param args
 */
	public static void main(String[] args) {
		System.out.println("Unesite format tabele za mine ");// Foriramo koliku tabelu za mine hocemo
		int formatTabele = TextIO.getInt();

		int[][] matrica = rasporediMine(napraviMatricu(formatTabele)); // Rasporedjujemo random mine po novoj matrici.
		int[][] tabelaZaIgru = popuniBrojevima(matrica);// U matricu zatim unosimo brojeve, tj popunjavamo u odnosu na radnom mine.
		ispisMatrice(tabelaZaIgru);// Ispisujemo matricu ( ovo mozemo izbrisati ali dok testiramo program cisto da vidimo kako citava tabela izgleda.

		int x = 0; // X koordinate
		int y = 0; // Y koordinate
		String[][] tablica = stringMatrica(tabelaZaIgru);// Nasa prazna string tabela
 
		int counter = 0; // Brojac kojeg cemo povecavati da bi znali kada smo dosli do kraja igre
		int brojMina = (int) (tablica.length * tablica.length * 0.3); // Broj mina.

		do { // Do - while petlja poziva funkciju igra sve dok igra nije gotova, tj ili smo pobjedili ili smo nagazili na minu.
			x = TextIO.getInt();
			y = TextIO.getInt();
			igraj(tablica, tabelaZaIgru, x, y); // pozivamo funkciju igra, ona ispisuje tabelu i otkriva broj pod zadanim koordinatama.
			counter++; // nakon unosa povecavamo brojac.
			
			if (tabelaZaIgru[x][y] == -1) { // Ukoliko smo nagazili na minu kraj programa
				System.out.println("Nagazili ste na minu, kraj igre !!!");
				return;
			}// Ukoliko smo otkrili sva polja !
			if (counter == (matrica.length*matrica.length - brojMina)) {
				System.out.println("Pobjedili ste, cestitamo !!!");

			}

		} while (counter < (matrica.length*matrica.length - brojMina));
	}
}
