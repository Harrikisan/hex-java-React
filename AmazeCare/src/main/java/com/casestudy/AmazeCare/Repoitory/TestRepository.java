package com.casestudy.AmazeCare.Repoitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.casestudy.AmazeCare.Model.Test;

public interface TestRepository extends JpaRepository<Test, Integer>{

	@Query("select t from Test t where t.lab.id=?1")
	List<Test> getByLabId(int labId);

}
