package kirill.industries.dto;

import java.util.ArrayList;
import java.util.List;

public class CompaniesResponse {
    private List<CompanyDto> companies = new ArrayList<>();

    public CompaniesResponse(List<CompanyDto> companies) {
        this.companies = companies;
    }

    public List<CompanyDto> getCompanies() {
        return companies;
    }

    public void setCompanies(List<CompanyDto> companies) {
        this.companies = companies;
    }
}
