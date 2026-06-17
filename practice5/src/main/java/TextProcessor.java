public class TextProcessor {

    /**
     * Метод для підрахунку голосних літер у слові англійського алфавіту
     * @param text
     * @return
     */
    public int countVowels(String text) {
        if (text == null) return 0;
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (char c : text.toCharArray()) {
            if (vowels.indexOf(c) != -1) count++;
        }
        return count;
    }

    /**
     * Метод, що перевіряє, чи закінчується рядок знаком оклику
     * @param text
     * @return
     */
    public boolean isExclamatory(String text) {
        if (text == null || text.isEmpty()) return false;
        return text.endsWith("!");
    }

    /**
     * Метод, що створює вітання
     * @param name
     * @param timeOfDay
     * @return
     */
    public String createGreeting(String name, String timeOfDay) {
        return "Good " + timeOfDay + ", " + name + "!";
    }
}