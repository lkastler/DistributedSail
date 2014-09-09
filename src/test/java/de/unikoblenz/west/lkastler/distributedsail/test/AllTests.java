package de.unikoblenz.west.lkastler.distributedsail.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ DistributedRepositoryTest.class, DistributedSailTest.class,
		DistributionTest.class })
/**
 * Test suite for all tests
 * @author lkastler
 */
public class AllTests {}
