package kirill.industries.dto;

public class CompanyDto {
    private String name;
    private String industry;

    public CompanyDto(String name, String industry) {
        this.name = name;
        this.industry = industry;
    }

    public CompanyDto() { }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
