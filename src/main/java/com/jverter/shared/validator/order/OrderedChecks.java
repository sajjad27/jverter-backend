package com.jverter.shared.validator.order;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;



@GroupSequence(value = {Default.class, First.class, Second.class, Third.class})
public interface OrderedChecks {}