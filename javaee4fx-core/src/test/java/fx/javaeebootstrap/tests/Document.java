package fx.javaeebootstrap.tests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Document {

    @NotNull
    private String author;

    @NotNull
    @Size(min = 10, max = 140)
    private String summary;

    @Min(20)
    private int pages;

    public Document(String author, String summary, int pages) {
        this.author = author;
        this.summary = summary;
        this.pages = pages;
    }

}
