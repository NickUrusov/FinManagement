package edu.innotech.repository;

import edu.innotech.model.Report;
import edu.innotech.model.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("""
            select report
            from Report report
            where report.id = :reportId
            """)
    public Report findByReportId(@Param("reportId") Long reportId);

    String typeSum = TypeTransaction.CREDITING.toString();

    @Query("""
            select sum(report.sumTransaction)
            from Transaction report
            where report.userId = :userId
            and report.dateTransaction >= :startDate
            and report.dateTransaction <= :endDate
            and report.typeTransaction = :typeSum
            """)
    public BigDecimal calculateSum(@Param("userId") Long userId
                                    , @Param("startDate") Date startDate
                                    , @Param("endDate") Date endDate
                                    , @Param("typeSum") TypeTransaction typeTransaction
                                    );
    @Query("""
            select report
            from Report report
            where report.userId = :userId
            and (report.startDate >= :startDate or :startDate is null)
            and (report.endDate <= :endDate or :endDate is null)
            """)
    public List<Report> findByUserId(@Param("userId") Long userId
                                    , @Param("startDate") Date startDate
                                    , @Param("endDate") Date endDate);
}
