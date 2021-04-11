package domain;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
public class Account {
	@NotNull
	private String provider;
	@NotNull
	private String identifier;
}
